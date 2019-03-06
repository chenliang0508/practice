JVM；
排序算法和 Java 集合&工具类；
多线程和并发包；
存储相关：Redis 、Elastic Search、MySQL；
框架：Spring，SpringMVC，Spring Boot
分布式：Dubbo；
设计模式；

1. String类为什么是final的。

多线程安全，将字符串对象保存在字符串常量池中共享效率高
不可变、共享、安全，缓存池 宏

2. HashMap的源码，实现原理，底层结构。

HashMap基于哈希表的 Map 接口的实现。允许使用 null 值和 null 键。此类不保证映射的顺序，特别是它不保证该顺序恒久不变。

值得注意的是HashMap不是线程安全的，如果想要线程安全的HashMap，可以通过Collections类的静态方法synchronizedMap获得线程安全的HashMap。

Map map = Collections.synchronizedMap(new HashMap());

HashMap的底层主要是基于数组和链表来实现的，它之所以有相当快的查询速度主要是因为它是通过计算散列码来决定存储的位置。HashMap中主要是通过key的hashCode来计算hash值的，只要hashCode相同，计算出来的hash值就一样。如果存储的对象对多了，就有可能不同的对象所算出来的hash值是相同的，这就出现了所谓的hash冲突。学过数据结构的同学都知道，解决hash冲突的方法有很多，HashMap底层是通过链表来解决hash冲突的。

HashMap其实也是一个线性的数组实现的,所以可以理解为其存储数据的容器就是一个线性数组。

HashMap其实就是一个Entry数组，Entry对象中包含了键和值，其中next也是一个Entry对象，它就是用来处理hash冲突的，形成一个链表。

默认初始容量为16，默认加载因子为0.75。

3. 说说你知道的几个Java集合类：list、set、queue、map实现类咯。。。

Java容器类类库的用途是"保存对象"，并将其划分为两个不同的概念：

1) Collection

一组"对立"的元素，通常这些元素都服从某种规则

　　1.1) List必须保持元素特定的顺序

　　1.2) Set不能有重复元素

　　1.3) Queue保持一个队列(先进先出)的顺序

2) Map

一组成对的"键值对"对象

Collection和Map的区别在于容器中每个位置保存的元素个数:

1) Collection 每个位置只能保存一个元素(对象)

2) Map保存的是"键值对"，就像一个小型数据库。我们可以通过"键"找到该键对应的"值"

1. Interface Iterable

迭代器接口，这是Collection类的父接口。实现这个Iterable接口的对象允许使用foreach进行遍历，也就是说，所有的Collection集合对象都具有"foreach可遍历性"。这个Iterable接口只有一个方法: iterator()。它返回一个代表当前集合对象的泛型<T>迭代器，用于之后的遍历操作

1.1 Collection

Collection是最基本的集合接口，一个Collection代表一组Object的集合，这些Object被称作Collection的元素。Collection是一个接口，用以提供规范定义，不能被实例化使用

1) Set

Set集合类似于一个罐子，"丢进"Set集合里的多个对象之间没有明显的顺序。Set继承自Collection接口，不能包含有重复元素(记住，这是整个Set类层次的共有属性)。

Set判断两个对象相同不是使用"=="运算符，而是根据equals方法。也就是说，我们在加入一个新元素的时候，如果这个新元素对象和Set中已有对象进行注意equals比较都返回false，则Set就会接受这个新元素对象，否则拒绝。

因为Set的这个制约，在使用Set集合的时候，应该注意两点：1) 为Set集合里的元素的实现类实现一个有效的equals(Object)方法、2) 对Set的构造函数，传入的Collection参数不能包含重复的元素

1.1) HashSet

HashSet是Set接口的典型实现，HashSet使用HASH算法来存储集合中的元素，因此具有良好的存取和查找性能。当向HashSet集合中存入一个元素时，HashSet会调用该对象的hashCode()方法来得到该对象的hashCode值，然后根据该HashCode值决定该对象在HashSet中的存储位置。

值得注意的是，HashSet集合判断两个元素相等的标准是两个对象通过equals()方法比较相等，并且两个对象的hashCode()方法的返回值相等

1.1.1) LinkedHashSet

LinkedHashSet集合也是根据元素的hashCode值来决定元素的存储位置，但和HashSet不同的是，它同时使用链表维护元素的次序，这样使得元素看起来是以插入的顺序保存的。

　　　　当遍历LinkedHashSet集合里的元素时，LinkedHashSet将会按元素的添加顺序来访问集合里的元素。

LinkedHashSet需要维护元素的插入顺序，因此性能略低于HashSet的性能，但在迭代访问Set里的全部元素时(遍历)将有很好的性能(链表很适合进行遍历)

1.2) SortedSet

此接口主要用于排序操作，即实现此接口的子类都属于排序的子类

1.2.1) TreeSet

TreeSet是SortedSet接口的实现类，TreeSet可以确保集合元素处于排序状态

1.3) EnumSet

EnumSet是一个专门为枚举类设计的集合类，EnumSet中所有元素都必须是指定枚举类型的枚举值，该枚举类型在创建EnumSet时显式、或隐式地指定。EnumSet的集合元素也是有序的，它们以枚举值在Enum类内的定义顺序来决定集合元素的顺序

2) List

List集合代表一个元素有序、可重复的集合，集合中每个元素都有其对应的顺序索引。List集合允许加入重复元素，因为它可以通过索引来访问指定位置的集合元素。List集合默认按元素的添加顺序设置元素的索引

2.1) ArrayList

ArrayList是基于数组实现的List类，它封装了一个动态的增长的、允许再分配的Object[]数组。

2.2) Vector

Vector和ArrayList在用法上几乎完全相同，但由于Vector是一个古老的集合，所以Vector提供了一些方法名很长的方法，但随着JDK1.2以后，java提供了系统的集合框架，就将Vector改为实现List接口，统一归入集合框架体系中

2.2.1) Stack

Stack是Vector提供的一个子类，用于模拟"栈"这种数据结构(LIFO后进先出)

2.3) LinkedList

implements List<E>, Deque<E>。实现List接口，能对它进行队列操作，即可以根据索引来随机访问集合中的元素。同时它还实现Deque接口，即能将LinkedList当作双端队列使用。自然也可以被当作"栈来使用"

3) Queue

Queue用于模拟"队列"这种数据结构(先进先出 FIFO)。队列的头部保存着队列中存放时间最长的元素，队列的尾部保存着队列中存放时间最短的元素。新元素插入(offer)到队列的尾部，访问元素(poll)操作会返回队列头部的元素，队列不允许随机访问队列中的元素。结合生活中常见的排队就会很好理解这个概念。

在java5中新增加了java.util.Queue接口，用以支持队列的常见操作。该接口扩展了java.util.Collection接口。

Queue使用时要尽量避免Collection的add()和remove()方法，而是要使用offer()来加入元素，使用poll()来获取并移出元素。它们的优点是通过返回值可以判断成功与否，add()和remove()方法在失败的时候会抛出异常。 如果要使用前端而不移出该元素，使用

element()或者peek()方法。

值得注意的是LinkedList类实现了Queue接口，因此我们可以把LinkedList当成Queue来用。

3.1) PriorityQueue

PriorityQueue并不是一个比较标准的队列实现，PriorityQueue保存队列元素的顺序并不是按照加入队列的顺序，而是按照队列元素的大小进行重新排序，这点从它的类名也可以看出来

3.2) Deque

Deque接口代表一个"双端队列"，双端队列可以同时从两端来添加、删除元素，因此Deque的实现类既可以当成队列使用、也可以当成栈使用

3.2.1) ArrayDeque

是一个基于数组的双端队列，和ArrayList类似，它们的底层都采用一个动态的、可重分配的Object[]数组来存储集合元素，当集合元素超出该数组的容量时，系统会在底层重新分配一个Object[]数组来存储集合元素

3.2.2) LinkedList

1.2 Map

Map用于保存具有"映射关系"的数据，因此Map集合里保存着两组值，一组值用于保存Map里的key，另外一组值用于保存Map里的value。key和value都可以是任何引用类型的数据。Map的key不允许重复，即同一个Map对象的任何两个key通过equals方法比较结果总是返回false。

关于Map，我们要从代码复用的角度去理解，java是先实现了Map，然后通过包装了一个所有value都为null的Map就实现了Set集合

Map的这些实现类和子接口中key集的存储形式和Set集合完全相同(即key不能重复)

Map的这些实现类和子接口中value集的存储形式和List非常类似(即value可以重复、根据索引来查找)

1) HashMap

和HashSet集合不能保证元素的顺序一样，HashMap也不能保证key-value对的顺序。并且类似于HashSet判断两个key是否相等的标准也是: 两个key通过equals()方法比较返回true、同时两个key的hashCode值也必须相等

1.1) LinkedHashMap

LinkedHashMap也使用双向链表来维护key-value对的次序，该链表负责维护Map的迭代顺序，与key-value对的插入顺序一致(注意和TreeMap对所有的key-value进行排序进行区分)

2) Hashtable

是一个古老的Map实现类

2.1) Properties

Properties对象在处理属性文件时特别方便(windows平台上的.ini文件)，Properties类可以把Map对象和属性文件关联起来，从而可以把Map对象中的key-value对写入到属性文件中，也可以把属性文件中的"属性名-属性值"加载到Map对象中

3) SortedMap

正如Set接口派生出SortedSet子接口，SortedSet接口有一个TreeSet实现类一样，Map接口也派生出一个SortedMap子接口，SortedMap接口也有一个TreeMap实现类

3.1) TreeMap

TreeMap就是一个红黑树数据结构，每个key-value对即作为红黑树的一个节点。TreeMap存储key-value对(节点)时，需要根据key对节点进行排序。TreeMap可以保证所有的key-value对处于有序状态。同样，TreeMap也有两种排序方式: 自然排序、定制排序

4) WeakHashMap

WeakHashMap与HashMap的用法基本相似。区别在于，HashMap的key保留了对实际对象的"强引用"，这意味着只要该HashMap对象不被销毁，该HashMap所引用的对象就不会被垃圾回收。但WeakHashMap的key只保留了对实际对象的弱引用，这意味着如果WeakHashMap对象的key所引用的对象没有被其他强引用变量所引用，则这些key所引用的对象可能被垃圾回收，当垃圾回收了该key所对应的实际对象之后，WeakHashMap也可能自动删除这些key所对应的key-value对

5) IdentityHashMap

IdentityHashMap的实现机制与HashMap基本相似，在IdentityHashMap中，当且仅当两个key严格相等(key1 == key2)时，IdentityHashMap才认为两个key相等

6) EnumMap

EnumMap是一个与枚举类一起使用的Map实现，EnumMap中的所有key都必须是单个枚举类的枚举值。创建EnumMap时必须显式或隐式指定它对应的枚举类。EnumMap根据key的自然顺序(即枚举值在枚举类中的定义顺序)



4. 描述一下ArrayList和LinkedList各自实现和区别

ArrayList是实现了基于动态数组的数据结构，LinkedList基于链表的数据结构。

对于随机访问get和set，ArrayList优于LinkedList，因为LinkedList要移动指针。

对于新增和删除操作add和remove，LinedList比较占优势，因为ArrayList要移动数据。

5. Java中的队列都有哪些，有什么区别。

普通队列（Queue）、阻塞队列、非阻塞队列

阻塞队列与普通队列的区别在于，当队列是空的时，从队列中获取元素的操作将会被阻塞，或者当队列是满时，往队列里添加元素的操作会被阻塞。试图从空的阻塞队列中获取元素的线程将会被阻塞，直到其他的线程往空的队列插入新的元素。同样，试图往已满的阻塞队列中添加新元素的线程同样也会被阻塞，直到其他的线程使队列重新变得空闲起来，如从队列中移除一个或者多个元素，或者完全清空队列。

JDK 7提供了7个阻塞队列，如下。

·ArrayBlockingQueue：一个由数组结构组成的有界阻塞队列。

·LinkedBlockingQueue：一个由链表结构组成的有界阻塞队列。

·PriorityBlockingQueue：一个支持优先级排序的无界阻塞队列。

·DelayQueue：一个使用优先级队列实现的无界阻塞队列。

·SynchronousQueue：一个不存储元素的阻塞队列。

·LinkedTransferQueue：一个由链表结构组成的无界阻塞队列。

·LinkedBlockingDeque：一个由链表结构组成的双向阻塞队列。

非阻塞队列ConcurrentLinkedQueue

ConcurrentLinkedQueue是一个基于链接节点的无界线程安全队列，它采用先进先出的规则对节点进行排序，当我们添加一个元素的时候，它会添加到队列的尾部；当我们获取一个元素时，它会返回队列头部的元素。

对于出队操作，也是使用CAS的方式循环尝试将元素从头部移除。

因为采用CAS操作，允许多个线程并发执行，并且不会因为加锁而阻塞线程，使得并发性能更好。

阻塞队列常用于生产者和消费者的场景，生产者是向队列里添加元素的线程，消费者是从队列里取元素的线程。阻塞队列就是生产者用来存放元素、消费者用来获取元素的容器。

在并发编程中，一般推荐使用阻塞队列，这样实现可以尽量地避免程序出现意外的错误（非阻塞算法堆死锁和优先级倒置有“免疫性”（但它们可能会出现饥饿和活锁，因为它们允许重进入））。阻塞队列使用最经典的场景就是socket客户端数据的读取和解析，读取数据的线程不断将数据放入队列，然后解析线程不断从队列取数据解析。还有其他类似的场景，只要符合生产者-消费者模型的都可以使用阻塞队列。

使用非阻塞队列，虽然能即时返回结果（消费结果），但必须自行编码解决返回为空的情况处理（以及消费重试等问题）。

另外他们都是线程安全的，不用考虑线程同步问题。

6. 反射中，Class.forName和classloader的区别

Class.forName(className)方法，内部实际调用的方法是 Class.forName(className,true,classloader);第2个boolean参数表示类是否需要初始化， Class.forName(className)默认是需要初始化。一旦初始化，就会触发目标对象的 static块代码执行，static参数也也会被再次初始化。

ClassLoader.loadClass(className)方法，内部实际调用的方法是 ClassLoader.loadClass(className,false);第2个 boolean参数，表示目标对象是否进行链接，false表示不进行链接，由上面介绍可以，不进行链接意味着不进行包括初始化等一些列步骤，那么静态块和静态对象就不会得到执行。

7. Java7、Java8的新特性(baidu问的,好BT)

Java7:

1，switch中可以使用字串了

2.运用List<String> tempList = new ArrayList<>(); 即泛型实例化类型自动推断

3.语法上支持集合，而不一定是数组final List<Integer> piDigits = [ 1,2,3,4,5,8 ];

4.新增一些取环境信息的工具方法

File System.getJavaIoTempDir() // IO临时文件夹

File System.getJavaHomeDir() // JRE的安装目录

File System.getUserHomeDir() // 当前用户目录

File System.getUserDir() // 启动java进程时所在的目录5

5.Boolean类型反转，空指针安全,参与位运算

Boolean Booleans.negate(Boolean booleanObj)

True => False , False => True, Null => Null

boolean Booleans.and(boolean[] array)

boolean Booleans.or(boolean[] array)

boolean Booleans.xor(boolean[] array)

boolean Booleans.and(Boolean[] array)

boolean Booleans.or(Boolean[] array)

boolean Booleans.xor(Boolean[] array)

6.两个char间的equals

boolean Character.equalsIgnoreCase(char ch1, char ch2)

7.安全的加减乘除

int Math.safeToInt(long value)

int Math.safeNegate(int value)

long Math.safeSubtract(long value1, int value2)

long Math.safeSubtract(long value1, long value2)

int Math.safeMultiply(int value1, int value2)

long Math.safeMultiply(long value1, int value2)

long Math.safeMultiply(long value1, long value2)

long Math.safeNegate(long value)

int Math.safeAdd(int value1, int value2)

long Math.safeAdd(long value1, int value2)

long Math.safeAdd(long value1, long value2)

int Math.safeSubtract(int value1, int value2)

8.map集合支持并发请求，且可以写成 Map map = {name:"xxx",age:18};

JAVA8：

java8的新特新逐一列出，并将使用简单的代码示例来指导你如何使用默认接口方法，lambda表达式，方法引用以及多重Annotation，之后你将会学到最新的API上的改进，比如流，函数式接口，Map以及全新的日期API

一、接口的默认方法

二、Lambda 表达式

三、函数式接口

四、方法与构造函数引用

五、Lambda 作用域

六、访问局部变量

七、访问对象字段与静态变量

八、访问接口的默认方法

九、Date API

十、Annotation 注解

8. Java数组和链表两种结构的操作效率，在哪些情况下(从开头开始，从结尾开始，从中间开始)，哪些操作(插入，查找，删除)的效率高

数组的查询效率较高，增删的效率很低，而链表却刚好相反。

数组有数组下标也就是序号，所以查询非常方便，只要你指定要哪个下标号码的值就行。

增删效率低也是因为需要维护这个序号。

链表不维护序号，所以增删直接操作，将前面指向他后面一个操作就完成了。但查询效率就低啊，得全链表扫描。

9. Java内存泄露的问题调查定位：jmap，jstack的使用等等

参考地址：http://josh-persistence.iteye.com/blog/2161848?utm_source=tuicool&utm_medium=referral

一、 jps(Java Virtual Machine Process Status Tool)：基础工具

主要用来输出JVM中运行的进程状态信息。jps [options] [hostid] 如果不指定hostid就默认为当前主机或服务器。

二、 jstack jstack主要用来查看某个Java进程内的线程堆栈信息。

jstack可以定位到线程堆栈，根据堆栈信息我们可以定位到具体代码，所以它在JVM性能调优中使用得非常多。

三、 jmap（Memory Map）和 jhat（Java Heap Analysis Tool）：

jmap导出堆内存，然后使用jhat来进行分析

jmap用来查看堆内存使用状况，一般结合jhat使用。

1、打印进程的类加载器和类加载器加载的持久代对象信息： jmap -permstat pid

2、查看进程堆内存使用情况:包括使用的GC算法、堆配置参数和各代中堆内存使用：jmap -heap pid

3、查看堆内存中的对象数目、大小统计直方图，如果带上live则只统计活对象：jmap -histo[:live] pid

4、还有一个很常用的情况是：用jmap把进程内存使用情况dump到文件中，再用jhat分析查看。需要注意的是 dump出来的文件还可以用MAT、VisualVM等工具查看。 注意如果Dump文件太大，可能需要加上-J-Xmx512m参数以指定最大堆内存，即jhat -J-Xmx512m -port 8888 /home/dump.dat。然后就可以在浏览器中输入主机地址:8888查看了：

四、jstat（JVM统计监测工具）: 看看各个区内存和GC的情况

五、hprof（Heap/CPU Profiling Tool）： hprof能够展现CPU使用率，统计堆内存使用情况。

10. string、stringbuilder、stringbuffer区别

1)可变与不可变

String类中使用字符数组保存字符串，如下就是，因为有“final”修饰符，所以可以知道string对象是不可变的。

private final char value[];

StringBuilder与StringBuffer都继承自AbstractStringBuilder类，在AbstractStringBuilder中也是使用字符数组保存字符串，如下就是，可知这两种对象都是可变的。

char[] value;

2)是否多线程安全

　　String中的对象是不可变的，也就可以理解为常量，显然线程安全。

　　AbstractStringBuilder是StringBuilder与StringBuffer的公共父类，定义了一些字符串的基本操作，如expandCapacity、append、insert、indexOf等公共方法。

　　StringBuffer对方法加了同步锁或者对调用的方法加了同步锁，所以是线程安全的。看如下源码：

public synchronized StringBuffer reverse() {

super.reverse();

return this;

}

 

public int indexOf(String str) {

return indexOf(str, 0); //存在 public synchronized int indexOf(String str, int fromIndex) 方法

}

　　StringBuilder并没有对方法进行加同步锁，所以是非线程安全的。

3)StringBuilder与StringBuffer共同点

　　StringBuilder与StringBuffer有公共父类AbstractStringBuilder(抽象类)。

抽象类与接口的其中一个区别是：抽象类中可以定义一些子类的公共方法，子类只需要增加新的功能，不需要重复写已经存在的方法；而接口中只是对方法的申明和常量的定义。

　　StringBuilder、StringBuffer的方法都会调用AbstractStringBuilder中的公共方法，如super.append(...)。只是StringBuffer会在方法上加synchronized关键字，进行同步。

　　最后，如果程序不是多线程的，那么使用StringBuilder效率高于StringBuffer。

11. hashtable和hashmap的区别

hashmap

线程不安全

允许有null的键和值

效率高一点、

方法不是Synchronize的要提供外同步

有containsvalue和containsKey方法

HashMap 是Java1.2 引进的Map interface 的一个实现

HashMap是Hashtable的轻量级实现

hashtable

线程安全

不允许有null的键和值

效率稍低、

方法是是Synchronize的

有contains方法方法

、Hashtable 继承于Dictionary 类

Hashtable 比HashMap 要旧

13 .异常的结构，运行时异常和非运行时异常，各举个例子



通常，Java的异常(包括Exception和Error)分为 可查的异常（checked exceptions）和不可查的异常（unchecked exceptions）。

可查异常（编译器要求必须处置的异常）： 正确的程序在运行中，很容易出现的、情理可容的异常状况 。 可查异常虽然是异常状况，但在一定程度上它的发生是可以预计的，而且一旦发生这种异常 状况，就必须采取某种方式进行处理。除了RuntimeException及其子类以外，其他的Exception类及其子类都属于可查异常。这种异常的特点是Java编译器会检查它，也就是说，当程序中可能出现这类异常，要么用try-catch语句捕获它，要么用throws子句声明抛出它，否则编译不会通过。

不可查异常(编译器不要求强制处置的异常):包括运行时异常（RuntimeException与其子类）和错误（Error）。

14. String a= “abc” String b = “abc” String c = new String(“abc”) String d = “ab” + “c” .他们之间用 == 比较的结果

System.out.println("a == b " + (a == b));//true

System.out.println("a == c " + (a == c));//false

System.out.println("a == d " + (a == d));//true

System.out.println("b == c " + (b == c));//false

System.out.println("b == d " + (b == d));//true

System.out.println("c == d " + (c == d));//false

对上面结果的解释说明：

首先上面的比较过程是直接拿 == 来比较的，没有用equal方法，那么用 == 来比较的话，比较的是地址。并不是值。

可以看到，a，b，d，三个字符串直接用 == 比较，比较出来的结果都是true。是相等的。

意思就是说这三个变量在空间上都是指向同一个内存地址。这就涉及到一个字符串常量池的问题。

但是和c比较的时候，却不相等，因为，c是new出来的，记得当时学习的时候，new都是在堆内存里面的，新开辟的空间。

所以，地址肯定就不相同。

15. String 类的常用方法

参考地址：https://www.cnblogs.com/crazyac/articles/2012791.html

16. Java 的引用类型有哪几种

Java虽然有内存管理机制，但仍应该警惕内存泄露的问题。例如对象池、缓存中的过期对象都有可能引发内存泄露的问题。

从JDK1.2版本开始，加入了对象的几种引用级别，从而使程序能够更好的控制对象的生命周期，帮助开发者能够更好的缓解和处理内存泄露的问题。

这几种引用级别由高到低分别为：强引用、软引用、弱引用和虚引用。

强引用:类似Object a=new Object()这类,永远不会被回收。

软引用:SoftReference,当系统快要发生内存溢出异常时，将会把这些对象列入回收范围进行二次回收，如果这次回收还是没有足够内存，则抛出内存溢出异常。

弱引用：比软引用更弱，活不过下一次gc。无论当前内存是否足够，下一次gc都会被回收掉。

虚引用：又叫幻引用，最弱，一个对象时候有虚引用的存在，不会对它的生存时间构成影响，唯一目的就是能在这对象被回收以后收到一个系统通知

在java.lang.ref包中提供了三个类：SoftReference类、WeakReference类和PhantomReference类，它们分别代表软引用、弱引用和虚引用。ReferenceQueue类表示引用队列，它可以和这三种引用类联合使用，以便跟踪Java虚拟机回收所引用的对象的活动。

17. 抽象类和接口的区别

抽象类是用来捕捉子类的通用特性的 。它不能被实例化，只能被用作子类的超类。抽象类是被用来创建继承层级里子类的模板。

接口是抽象方法的集合。如果一个类实现了某个接口，那么它就继承了这个接口的抽象方法。这就像契约模式，如果实现了这个接口，那么就必须确保使用这些方法。接口只是一种形式，接口自身不能做任何事情。

参数

抽象类

接口

默认的方法实现

它可以有默认的方法实现

接口完全是抽象的。它根本不存在方法的实现

实现

子类使用extends关键字来继承抽象类。如果子类不是抽象类的话，它需要提供抽象类中所有声明的方法的实现。

子类使用关键字implements来实现接口。它需要提供接口中所有声明的方法的实现

构造器

抽象类可以有构造器

接口不能有构造器

与正常Java类的区别

除了你不能实例化抽象类之外，它和普通Java类没有任何区别

接口是完全不同的类型

访问修饰符

抽象方法可以有public、protected和default这些修饰符

接口方法默认修饰符是public。你不可以使用其它修饰符。

main方法

抽象方法可以有main方法并且我们可以运行它

接口没有main方法，因此我们不能运行它。

多继承

抽象方法可以继承一个类和实现多个接口

接口只可以继承一个或多个其它接口

速度

它比接口速度要快

接口是稍微有点慢的，因为它需要时间去寻找在类中实现的方法。

添加新方法

如果你往抽象类中添加新的方法，你可以给它提供默认的实现。因此你不需要改变你现在的代码。

如果你往接口中添加方法，那么你必须改变实现该接口的类。

什么时候使用抽象类和接口：

如果你拥有一些方法并且想让它们中的一些有默认实现，那么使用抽象类吧。

如果你想实现多重继承，那么你必须使用接口。由于Java不支持多继承，子类不能够继承多个类，但可以实现多个接口。因此你就可以使用接口来解决它。

如果基本功能在不断改变，那么就需要使用抽象类。如果不断改变基本功能并且使用接口，那么就需要改变所有实现了该接口的类。

18. java的基础类型和字节大小。

在Java中一共有8种基本数据类型，其中有4种整型，2种浮点类型，1种用于表示Unicode编码的字符单元的字符类型和1种用于表示真值的boolean类型。（一个字节等于8个bit）

1)整型

类型 存储需求 bit数 取值范围 备注

int 4字节 4*8

short 2字节 2*8 －32768～32767

long 8字节 8*8

byte 1字节 1*8 －128～127

2)浮点型

类型 存储需求 bit数 取值范围 备注

float 4字节 4*8 float类型的数值有一个后缀F(例如：3.14F)

double 8字节 8*8 没有后缀F的浮点数值(如3.14)默认为double类型

3)char类型

类型 存储需求 bit数 取值范围 备注

char 2字节 2*8

4)boolean类型

类型 存储需求 bit数 取值范围 备注

boolean 1字节 1*8 false、true

补充：Java有一个能够表示任意精度的算书包，通常称为“大数值”(big number)。虽然被称为大数值，但它并不是一种Java类型，而是一个Java对象。

如果基本的整数和浮点数精度不能够满足需求，那么可以使用java.math包中的两个很有用的类：BigIntegerBigDecimal(Android SDK中也包含了java.math包以及这两个类)这两个类可以处理包含任意长度数字序列的数值。BigInteger类实现了任意精度的整数运算，BigDecimal实现了任意精度的浮点数运算。具体的用法可以参见Java API。

19. Hashtable,HashMap,ConcurrentHashMap 底层实现原理与线程安全问题（建议熟悉 jdk 源码，才能从容应答）

关键点：

HashTable容器使用synchronized来保证线程安全，但在线程竞争激烈的情况下HashTable的效率非常低下。因为当一个线程访问HashTable的同步方法时，其他线程访问HashTable的同步方法时，可能会进入阻塞或轮询状态。如线程1使用put进行添加元素，线程2不但不能使用put方法添加元素，并且也不能使用get方法来获取元素，所以竞争越激烈效率越低。

ConcurrentHashMap的锁分段技术：ConcurrentHashMap是由Segment数组结构和HashEntry数组结构组成。Segment是一种可重入锁ReentrantLock，在ConcurrentHashMap里扮演锁的角色，HashEntry则用于存储键值对数据。一个ConcurrentHashMap里包含一个Segment数组，Segment的结构和HashMap类似，是一种数组和链表结构， 一个Segment里包含一个HashEntry数组，每个HashEntry是一个链表结构的元素， 每个Segment守护者一个HashEntry数组里的元素,当对HashEntry数组的数据进行修改时，必须首先获得它对应的Segment锁。

20. 如果不让你用Java Jdk提供的工具，你自己实现一个Map，你怎么做。说了好久，说了HashMap源代码，如果我做，就会借鉴HashMap的原理，说了一通HashMap实现

参考地址：

21. Hash冲突怎么办？哪些解决散列冲突的方法？

开放地址法：

1)线性探测法:ThreadLocalMap

线性再散列法是形式最简单的处理冲突的方法。插入元素时，如果发生冲突，算法会简单的从该槽位置向后循环遍历hash表，直到找到表中的下一个空槽，并将该元素放入该槽中（会导致相同hash值的元素挨在一起和其他hash值对应的槽被占用）。查找元素时，首先散列值所指向的槽，如果没有找到匹配，则继续从该槽遍历hash表，直到：（1）找到相应的元素；（2）找到一个空槽，指示查找的元素不存在，（所以不能随便删除元素）；（3）整个hash表遍历完毕（指示该元素不存在并且hash表是满的）

用线性探测法处理冲突，思路清晰，算法简单，但存在下列缺点：

① 处理溢出需另编程序。一般可另外设立一个溢出表，专门用来存放上述哈希表中放不下的记录。此溢出表最简单的结构是顺序表，查找方法可用顺序查找。

② 按上述算法建立起来的哈希表，删除工作非常困难。如果将此元素删除，查找的时会发现空槽，则会认为要找的元素不存在。只能标上已被删除的标记，否则，将会影响以后的查找。

③ 线性探测法很容易产生堆聚现象。所谓堆聚现象，就是存入哈希表的记录在表中连成一片。按照线性探测法处理冲突，如果生成哈希地址的连续序列愈长 ( 即不同关键字值的哈希地址相邻在一起愈长 ) ，则当新的记录加入该表时，与这个序列发生冲突的可能性愈大。因此，哈希地址的较长连续序列比较短连续序列生长得快，这就意味着，一旦出现堆聚 ( 伴随着冲突 ) ，就将引起进一步的堆聚。

2)线性补偿探测法

线性补偿探测法的基本思想是：将线性探测的步长从 1 改为 Q ，即将上述算法中的

hash ＝ (hash ＋ 1) % m 改为：hash ＝ (hash ＋ Q) % m = hash % m + Q % m，而且要求 Q 与 m 是互质的，以便能探测到哈希表中的所有单元。

【例】 PDP-11 小型计算机中的汇编程序所用的符合表，就采用此方法来解决冲突，所用表长 m ＝ 1321 ，选用 Q ＝ 25 。

3)伪随机探测

随机探测的基本思想是：将线性探测的步长从常数改为随机数，即令： hash ＝ (hash ＋ RN) % m ，其中 RN 是一个随机数。在实际程序中应预先用随机数发生器产生一个随机序列，将此序列作为依次探测的步长。这样就能使不同的关键字具有不同的探测次序，从而可以避 免或减少堆聚。基于与线性探测法相同的理由，在线性补偿探测法和随机探测法中，删除一个记录后也要打上删除标记。

拉链法 : hashmap

拉链法的优点

与开放定址法相比，拉链法有如下几个优点：

①拉链法处理冲突简单，且无堆积现象，即非同义词决不会发生冲突，因此平均查找长度较短；

②由于拉链法中各链表上的结点空间是动态申请的，故它更适合于造表前无法确定表长的情况；

③开放定址法为减少冲突，要求装填因子α较小，故当结点规模较大时会浪费很多空间。而拉链法中可取α≥1，且结点较大时，拉链法中增加的指针域可忽略不计，因此节省空间；

④在用拉链法构造的散列表中，删除结点的操作易于实现。只要简单地删去链表上相应的结点即可。

拉链法的缺点：拉链法的缺点是：指针需要额外的空间，故当结点规模较小时，开放定址法较为节省空间，而若将节省的指针空间用来扩大散列表的规模，可使装填因子变小，这又减少了开放定址法中的冲突，从而提高平均查找速度。

再散列（双重散列，多重散列）

当发生冲突时，使用第二个、第三个、哈希函数计算地址，直到无冲突时。缺点：计算时间增加。

建立一个公共溢出区

假设哈希函数的值域为[0,m-1],则设向量HashTable[0..m-1]为基本表，另外设立存储空间向量OverTable[0..v]用以存储发生冲突的记录。

22. HashMap冲突很厉害，最差性能，你会怎么解决?从O（n）提升到log（n）咯，用二叉排序树的思路说了一通

 

23. rehash

HashMap的内部实现机制时提到了两个参数，DEFAULT_INITIAL_CAPACITY和DEFAULT_LOAD_FACTOR，DEFAULT_INITIAL_CAPACITY是table数组的容量，DEFAULT_LOAD_FACTOR则是为了最大程度避免哈希冲突，提高HashMap效率而设置的一个影响因子，将其乘以DEFAULT_INITIAL_CAPACITY就得到了一个阈值threshold，当HashMap的容量达到threshold时就需要进行扩容，这个时候就要进行ReHash操作了，可以看到下面addEntry函数的实现，当size达到threshold时会调用resize函数进行扩容。

在扩容的过程中需要进行ReHash操作，而这是非常耗时的，在实际中应该尽量避免。

24. hashCode() 与 equals() 生成算法、方法怎么重写

两个obj，如果equals()相等，hashCode()一定相等。

两个obj，如果hashCode()相等，equals()不一定相等（Hash散列值有冲突的情况，虽然概率很低）。

