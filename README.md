#### 并发问题
    程序在单线程运行时往往没有问题，但一旦变成多线程执行操作就可能造成并发问题，java提供了各种锁来帮助开发规避并发问题，中间
    尤为突出的技术就是volatile 和 synchronized。关于这两个关键字的面试题也层出不穷。

### synchronized 和 volatile 的联系于区别
    相同点：
    synchronized 能保证原子性、可见性、顺序性
    volatile 能保证可见性、顺序性
    
    不同点：
    synchronized 保证顺序性的方式和volatile的实现方式不同
    volatile 不能保证原子性，所以依然存在并发问题
    
    原子性：一个或一组操作要么都成功，要么都失败
    可见性：一个线程对共享变量的操作其他线程能感知到，其实这里的可见性原理是分亮点
              1、如果主内存共享变量被修改，则其他线程的共享变量地址会失效，导致重新读取
    顺序性：JVM会重新编排无依赖关系的程序，单线程不受影响，但是多线程会造成并发问题，synchronized 和 volatile的处理方式也各有区别
    
### synchronized 是如何保证原子性、有序性、可见性
    1、synchronized会将对象上锁，导致此逻辑只能单个线程运行，其他线程不结束但是一直等待，所以对于共享变量来说相当于单线程运行
    2、有序性的问题是因为指令重排，但是synchronized锁了当前逻辑就相当于单线程执行，整个逻辑是原子的，所以即使指令重排也不会因为多线程的结果。通过原子性规避指令重排
    3、可见性是因为synchronized调用会执行lock的字节码指令，这个指令的作用是强制刷新主内存共享变量，且其关联的备份地址失效
    
### volatile 是如何保证有序性、可见性
    1、volatile不支持原子性
    2、有序性是通过内存屏障，这个屏障会禁止指令重排，从而保证代码的有序性
    3、可见性和 synchronized 的机制一样，也是工作内存的共享变量会即使刷新到主内存，其他副本地址失效，重新获取
    
### synchronized 是可重入锁吗？底层原理是什么
    是可重入锁，通过javap反汇编我们看到synchronized使用编程了monitorentor和monitorexit两个指令，每个锁对象
    会关联一个monitor（监视器，它才是真正的锁对象），它内部有两个重要的成员变量：owner会保存获得锁的线程，recursions
    会保存线程获得锁的次数。当执行到monitorexit时，recursions会 -1， 当计算器减到0时这个线程就会释放。

### synchronized 异常会释放锁吗？底层原理是什么
    会释放锁 底层是因为异常之后依然会执行monitorexit机器码指令，这个指令解锁，详情看下面复制代码
    public static void main(java.lang.String[]);
        descriptor: ([Ljava/lang/String;)V
        flags: (0x0009) ACC_PUBLIC, ACC_STATIC
        Code:
          stack=2, locals=3, args_size=1
             0: getstatic     #7                  // Field obj:Ljava/lang/Object;
             3: dup
             4: astore_1
             5: monitorenter
             6: getstatic     #13                 // Field java/lang/System.out:Ljava/io/PrintStream;
             9: ldc           #19                 // String 1
            11: invokevirtual #21                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
            14: aload_1
      这里-->15: monitorexit
            16: goto          24
            19: astore_2
            20: aload_1
     这里-->21: monitorexit
            22: aload_2
            23: athrow
            24: return
          Exception table:
             from    to  target type
                 6    16    19   any ===> 6-16的异常会跳至19行继续执行，看上面的复制javap -p -v
                19    22    19   any
          LineNumberTable:
            line 14: 0
            line 15: 6
            line 16: 14
            line 17: 24
          StackMapTable: number_of_entries = 2
            frame_type = 255 /* full_frame */
              offset_delta = 19
              locals = [ class "[Ljava/lang/String;", class java/lang/Object ]
              stack = [ class java/lang/Throwable ]
            frame_type = 250 /* chop */
              offset_delta = 4

### synchronized 修饰 方法 和 对象时有什么不同？
    修饰对象时，底层会显示执行monitorentor和monitorexit两个指令
    修饰方法时，底层会隐式执行monitorentor和monitorexit两个指令，方法会标记这个（ACC_SYNCHRONIZED）
    什么叫隐式呢？看下面复制指令：
     public synchronized void test();
        descriptor: ()V
        flags: (0x0021) ACC_PUBLIC, ACC_SYNCHRONIZED
        Code:
          stack=2, locals=1, args_size=1
             0: getstatic     #13                 // Field java/lang/System.out:Ljava/io/PrintStream;
             3: ldc           #27                 // String a
             5: invokevirtual #21                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
             8: return
          LineNumberTable:
            line 20: 0
            line 21: 8
     标记（ACC_SYNCHRONIZED）有什么用？
     会隐式调用monitorentor和monitorexit两个指令，不需要显示调用，默认规则。
### synchronized 和 Lock的区别 [https://blog.csdn.net/hefenglian/article/details/82383569]
    1、synchronized是关键字， 而Lock是一个接口。
    2、synchronized会自动释放锁， 而Lock必须手动释放锁。
    3、synchronized是不可中断的， Lock可以中断也可以不中断。
    4、通过Lock可以知道线程有没有拿到锁，而synchronized不能
    5、synchronized能锁住方法和代码块，而Lock只能锁住代码块
    6、Lock可以使用读锁提高多线程读效率
    7、synchronized是非公平锁，ReentrantLock可以控制是否是公平锁
    扩展：
    ReentrantLock怎么控制公平锁？底层原理？
    读写锁底层原理？
    
### 锁消除
    锁消除是指虚拟机即时编译器（JIT）在运行时，对一些代码上要求同步，但是被检测到不可能存在共享数据竞争的锁进行消除。
    锁消除的主要判断依据来源于逃逸分析的数据支持，如果判断在一段代码中，堆上的所有数据都不会逃逸出去从而被其他线程访问到。
    简短：即时一个方法用了synchronized关键字同步，但是JIT通过逃逸分析发现并没有逃逸数据，则这个同步效果被消除，提升性能。
    
### 锁粗化
    什么是锁粗化？ JVM会探测到一连串小的操作都使用同一个对象加锁，将同步代码块的范围放大，放到这串操作的外面，这样只需要加一次锁即可。
    简短：就是一个循环体里面有小锁频繁加和解，那么就会将这把锁粗化到循环体外层，减少加锁和解锁的性能消耗。

### ConcurrentHashMap 的同步机制是怎么实现的？
    我们知道ConcurrentHashMap是同步的，那到底是怎么实现的呢，我们做一个简短的介绍，ConcurrentHashMap其实对读方法并没有加限制，也就是读并不会加锁，对写是有加锁机制的，我们着重
    讲一下写的加锁。
    
    写加锁也分乐观锁和重量级锁
    1、首先第一个数据put会进行节点初始化
    2、如果当前数据不存在hash冲突就直接用cas（乐观锁）进行数据添加
    3、如果当前数据存在hash冲突，我们就锁住这个链表的第一个元素，类似于行锁，这样其他线程要在这个被锁链表添加数据需要等待
        3.1、如果链表则遍历链表，key相同则覆盖val，反之则添加到链表尾部
        3.2、如果是红黑树则根据红黑树规则添加
        3.3、如果链表长度大于等于8则转换成红黑树，提升查询效率
    至此 ConcurrentHashMap 的同步策略已经说完了，总结起来就是没有hash冲突用乐观锁，有hash冲突用synchronized锁住链表头，从而达到同步的效果

### 讲一下锁升级的过程
    首先一个对象起初是无锁状态，也就是初始状态，当有一个线程进来的时候就升级成了偏向锁，偏向锁的优势在于不锁任何对象之对比是不是当前线程即可，效率较高
    当有其他线程也来竞争锁的时候，锁就被升级成轻量级锁，轻量级锁的优势在于性能消耗在特定情况下比重量级锁要小，但是如果竞争锁的线程偏多的时候就会导致轻量级锁的性能消耗偏大，
    所以轻量级锁只能在线程竞争不激烈的情况下适用，当竞争激烈前期，没有抢到锁的线程会进行自旋来获取锁，jvm默认是10次，后期优化是自适应自旋，轻量级锁通过jvm自己判断自旋次数，
    当达到最大次数还未获取到锁的时候锁就会升级成重量级锁，重量级锁的坏处很明显，锁消耗比较大，在于它会在用户态和内核态之间频繁转换。--- 当然我们如果真的需要用到锁可以考虑
    juc框架的读写锁来提升性能也是不错的选择。
  
    
[数据签名和验签](./src/test/java/com/cas/rsa/SHA256withRSATest.java)  
[webservice服务端配置](./src/main/java/com/cas/controller/webservice/config/CxfConfig.java)  
[webservice服务端接口](./src/main/java/com/cas/controller/webservice/WebServiceI.java)  
[webservice客户端](./src/test/java/com/cas/webservice/TestHelloWrold.java)  
[RSA工具类](./src/test/java/com/cas/rsa/RSAUtils.java)
[SM4工具类](./src/test/java/com/cas/sm4/Sm4Util.java)  
[SM4工具类](./src/test/java/com/cas/sm4/Sm4Util.java)

[BASE64图片转换](./src/test/java/com/cas/img/Base64ImgTest.java)
[]