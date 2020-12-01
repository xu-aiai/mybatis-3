# mybatis-learning

## 1.作用域（Scope）和生命周期

**SqlSessionFactoryBuilder**

这个类可以被实例化、使用和丢弃，一旦创建了 SqlSessionFactory，就不再需要它了。 因此 <font color="FF0000">SqlSessionFactoryBuilder 实例的最佳作用域是方法作用域（也就是局部方法变量）</font>。 你可以重用 SqlSessionFactoryBuilder 来创建多个 SqlSessionFactory 实例，但最好还是不要一直保留着它，以保证所有的 XML 解析资源可以被释放给更重要的事情。

**SqlSessionFactory**

SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例。 使用 SqlSessionFactory 的最佳实践是在应用运行期间不要重复创建多次，多次重建 SqlSessionFactory 被视为一种代码“坏习惯”。因此 <font color="FF0000">SqlSessionFactory的最佳作用域是应用作用域。 有很多方法可以做到，最简单的就是使用单例模式或者静态单例模式。</font> 

**SqlSession**

每个线程都应该有它自己的 SqlSession 实例。SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。 <font color="FF0000">绝对不能将 SqlSession 实例的引用放在一个类的静态域，甚至一个类的实例变量也不行。 也绝不能将 SqlSession 实例的引用放在任何类型的托管作用域中，比如 Servlet 框架中的 HttpSession。</font> 如果你现在正在使用一种 Web 框架，考虑 <font color="FF0000">将 SqlSession 放在一个和 HTTP 请求相似的作用域中</font>。 换句话说，每次收到 HTTP 请求，就可以打开一个 SqlSession，返回一个响应后，就关闭它。 这个关闭操作很重要，为了确保每次都能执行关闭操作，你应该把这个关闭操作放到 finally 块中。 下面的示例就是一个确保 SqlSession 关闭的标准模式：

```java
try (SqlSession session = sqlSessionFactory.openSession()) {
  // 你的应用逻辑代码
}
```

**映射器实例**

映射器是一些绑定映射语句的接口。 <font color="FF0000">映射器接口的实例是从 SqlSession 中获得</font>的。虽然从技术层面上来讲，任何映射器实例的最大作用域与请求它们的 SqlSession 相同。但方法作用域才是映射器实例的最合适的作用域。 也就是说，映射器实例应该在调用它们的方法中被获取，使用完毕之后即可丢弃。 映射器实例并不需要被显式地关闭。尽管在整个请求作用域保留映射器实例不会有什么问题，但是你很快会发现，在这个作用域上管理太多像 SqlSession 的资源会让你忙不过来。 因此，最好将映射器放在方法作用域内。

```java
try (SqlSession session = sqlSessionFactory.openSession()) {
  BlogMapper mapper = session.getMapper(BlogMapper.class);
  // 你的应用逻辑代码
}
```

## 2. Mybatis 配置文件解析

MyBatis 的配置文件包含了会深深影响 MyBatis 行为的设置和属性信息。 配置文档的顶层结构如下：

configuration（配置）

- [properties（属性）](https://mybatis.org/mybatis-3/zh/configuration.html#properties)
- [settings（设置）](https://mybatis.org/mybatis-3/zh/configuration.html#settings)
- [typeAliases（类型别名）](https://mybatis.org/mybatis-3/zh/configuration.html#typeAliases)
- [typeHandlers（类型处理器）](https://mybatis.org/mybatis-3/zh/configuration.html#typeHandlers)
- [objectFactory（对象工厂）](https://mybatis.org/mybatis-3/zh/configuration.html#objectFactory)
- [plugins（插件）](https://mybatis.org/mybatis-3/zh/configuration.html#plugins)
- environments（环境配置）
  - environment（环境变量）
    - transactionManager（事务管理器）
    - dataSource（数据源）
- [databaseIdProvider（数据库厂商标识）](https://mybatis.org/mybatis-3/zh/configuration.html#databaseIdProvider)
- [mappers（映射器）](https://mybatis.org/mybatis-3/zh/configuration.html#mappers)

<font color="FF0000">mybatis 的配置文件需要按照上述结构和顺序进行编写，顺序不对，mybatis 解析不了</font>

**引言**

Node 接口是整个文档对象模型 (Document Object Model-DOM)的 数据类型，表示文档树形结构中的每个节点。接口中定义了一系列处理节点的方法。

XML 文档中的每个成分都是一个节点。整个文档是一个文档节点；每个 XML 元素是一个元素节点；包含在 XML 元素中的文本是文本节点；每一个 XML 属性是一个属性节点；注释是注释节点。

Element 接口是 HTML 或者 XML 文档中元素的数据类型。每个元素有很多的属性，因此 Element 接口继承了 Node 接口。一般 Node 接口被用于检索一个元素的所有属性集合。Element 接口中的方法用于通过名字检索属性对象或者属性值。在 XML 中，属性值可能又是一个实体引用。即属性值为一颗文档子树。

XML DOM 教程：https://www.runoob.com/dom/dom-tutorial.html

XPath 接口，XML 的路径，用来确定 XML 文档中某部分的位置。XPath 基于 XML 的树状结构，有在数据结构树中寻找节点的能力。

https://www.runoob.com/xpath/xpath-tutorial.html

**XNode 类**

在 Mybatis 中，定义了 XNode 类用于解析XPath XML 文件，XNode 类 是 JDK 中定义的 Node 类的增强类。

**XPathParser**

这个类是解析 mybatis XML 文件的关键类，后续在补充其作用。

**properties**

如果一个属性在不只一个地方进行了配置，那么，MyBatis 将按照下面的顺序来加载：

- 首先读取在 properties 元素体内指定的属性。
- 然后根据 properties 元素中的 resource 属性读取类路径下属性文件，或根据 url 属性指定的路径读取属性文件，并覆盖之前读取过的同名属性。
- 最后读取作为方法参数传递的属性，并覆盖之前读取过的同名属性。

因此，通过方法参数传递的属性具有最高优先级，resource/url 属性中指定的配置文件次之，最低优先级的则是 properties 元素中指定的属性。



## 3. SqlSessionFactory

SqlSessionFactory 有六个方法创建 SqlSession 实例。通常来说，选择什么方法创建，需要考虑以下几点：

- **事务处理**：你希望在 session 作用域中使用事务作用域，还是使用自动提交(auto-commit)，对很多数据库或者JDBC驱动来说，等同于关闭事务支持。
- **数据库连接**：你希望 Mybatis 帮你从已经配置的数据源中获取连接，还是使用自己提供的连接？
- **语句执行**：你希望 MyBatis 复用 PreparedStatement 或批量更新语句(包括插入语句和删除语句)嘛？

```java
SqlSession openSession()
SqlSession openSession(boolean autoCommit)
SqlSession openSession(Connection connection)
SqlSession openSession(TransactionIsolationLevel level)
SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level)
SqlSession openSession(ExecutorType execType)
SqlSession openSession(ExecutorType execType, boolean autoCommit)
SqlSession openSession(ExecutorType execType, Connection connection)
Configuration getConfiguration();
```

以上为已重载的 openSession() 方法提供使用。

根据上述的方法签名(方法名称和参数列表) 可以大致知道每个重载方法的区别。

向 `autoCommit` 可选参数传递 `true` 值即可开启自动提交功能。若要使用自己的 `Connection` 实例，传递一个 `Connection` 实例给 `connection` 参数即可。特别注意：我们没有提供同时设置 `Connection` 和 `autoCommit` 的方法，这是因为 MyBatis 会依据传入的 Connection 来决定是否启用 autoCommit。

<font color="FF0000">对于事务隔离级别，MyBatis 使用了一个 Java 枚举包装器来表示，称为 `TransactionIsolationLevel`，事务隔离级别支持 JDBC 的五个隔离级别（`NONE`、`READ_UNCOMMITTED`、`READ_COMMITTED`、`REPEATABLE_READ` 和 `SERIALIZABLE`），并且与预期的行为一致。</font>

 **ExecutorType**（这个东西好像挺重要的，先记一下，后面遇到了在研究）

- `ExecutorType.SIMPLE`：该类型的执行器没有特别的行为。它为每个语句的执行创建一个新的预处理语句。
- `ExecutorType.REUSE`：该类型的执行器会复用预处理语句。
- `ExecutorType.BATCH`：该类型的执行器会批量执行所有更新语句，如果 SELECT 在多个更新中间执行，将在必要时将多条更新语句分隔开来，以方便理解。

## 4. SqlSession

SqlSession 在 Mybatis 中是一个很强大的类，它包含了所有执行语句，提交或回滚事务以及获取映射器实例的方法。

SqlSession 的方法很多。可以分为以下几种组别

**语句执行方法**

这些方法被用来执行定义在 SQL 映射 XML 文件中的 SELECT，INSERT，UPDATE 和 DELETE 语句。你可以通过名字快速了解它的作用，每一方法都接受语句的 ID 以及参数对象，参数可以是原始类型(支持自动装箱或包装类)、JavaBean，POJO 或 Map。

```java
<T> T selectOne(String statement, Object parameter)
<E> List<E> selectList(String statement, Object parameter)
<T> Cursor<T> selectCursor(String statement, Object parameter)
<K,V> Map<K,V> selectMap(String statement, Object parameter, String mapKey)
int insert(String statement, Object parameter)
int update(String statement, Object parameter)
int delete(String statement, Object parameter)
```

selectOne 和 selectList 的不同仅仅是 selectOne 必须返回一个对象或者 值。如果返回值多于一个，就会抛出异常。如果你不知道返回对象有多少，请使用 selectList。

如果需要查看某个对象是否存在，最好的办法是查询一个 count 值。

selectMap 稍微特殊一点，它会将返回对象的其中一个属性作为 key 值，将对象作为 value 值，从而将多个结果集转为 Map 类型值。

insert，update，delete 方法返回的值表示受该语句影响的行数。

**立即批量更新方法**

当你将`ExecutorType` 设置为 `ExecutorType.BATCCH` 时，可以使用这个方法清除(执行)缓存在JDBC驱动类中的批量更新语句。

```java 
List<BatchResult> flushStatements()
```

**事务控制方法**

有四个方法用来控制事务作用域。当然，如果你已经设置了自动提交或你使用了外部事务管理器，这些方法就没什么用了。然而，如果你正在使用由 Connection 实例控制的 JDBC 事务管理器，那么这四个方法就会派上用场。

```java
void commit()
void commit(boolean force)
void rollback()
void rollback(boolean force)
```

默认情况下 Mybatis 不会自动提交事务，除非它侦测到调用了插入，更新或删除方法改变了数据库。如果你没有使用这些方法提交修改，那么你可以在 commit 和 rollback 方法参数中传入 true 值，来保证事务被正常提交。

## 5. 缓存

Mybatis 中使用到了两种缓存，本地缓存和二级缓存。

每当一个新Session被创建，Mybatis 就会创建一个与之相关联的本地缓存。任何在 session 执行过的查询结果都会别保存在本地缓存中，所以当再次执行参数相同的相同查询时，就不需要实际查询数据库。 本地缓存将会在做出修改、事务提交或回滚，以及关闭 session 时清空。

默认情况下，本地缓存数据的生命周期等同于整个 session 周期。由于缓存会被用来解决循环引用问题和加快重复嵌套查询的速度，所以无法将其完全禁用。但是你可以设置 localCacheScope=STATEMENT 来只在语句执行时使用缓存。

注意：如果 localCacheSCope=SESSION，对于某个对象，Mybatis 将返回在本地缓存中唯一对象的引用。对返回的对象(例如 list)做出的任何修改将会影响本地缓存的内容，进而影响到在本次 session 中从缓存返回的值。因此，不要对 Mybatis 所返回的对象做出修改，以防后患。

调用下面方法可以清空本地缓存。

```java
void clearCache()
```

确保 SqlSession 被关闭

```java
void close()
```

对于打开的任何 session ，都要保证它们被妥善关闭，这很重要。

```java
SqlSession session = sqlSessionFactory.openSession();
try (SqlSession session = sqlSessionFactory.openSession()) {
    // 假设下面三行代码是你的业务逻辑
    session.insert(...);
    session.update(...);
    session.delete(...);
    session.commit();
}
```









什么是上下文：https://www.zhihu.com/question/26387327

Date 和 TimeStamp 的区别**