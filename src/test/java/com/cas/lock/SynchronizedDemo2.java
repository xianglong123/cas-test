package com.cas.lock;

/**
 * >javac SynchronizedDemo2.java
 * >javap -verbose SynchronizedDemo2.class
 *
 *
 *
 */
public class SynchronizedDemo2 {

    Object object = new Object();
    public void method1() {
        synchronized (object) {

        }
        method2();
    }

    private static void method2() {

    }

    //(base) xianglong@localhost lock % javap -verbose SynchronizedDemo2.class
    //Classfile /Users/xianglong/IdeaProjects/cas-test2/src/test/java/com/cas/lock/SynchronizedDemo2.class
    //  Last modified 2023-5-8; size 514 bytes
    //  MD5 checksum 17d324ba4bd2add9e2e2bf45d9071a46
    //  Compiled from "SynchronizedDemo2.java"
    //public class com.cas.lock.SynchronizedDemo2
    //  minor version: 0
    //  major version: 52
    //  flags: ACC_PUBLIC, ACC_SUPER
    //Constant pool:
    //   #1 = Methodref          #2.#20         // java/lang/Object."<init>":()V
    //   #2 = Class              #21            // java/lang/Object
    //   #3 = Fieldref           #5.#22         // com/cas/lock/SynchronizedDemo2.object:Ljava/lang/Object;
    //   #4 = Methodref          #5.#23         // com/cas/lock/SynchronizedDemo2.method2:()V
    //   #5 = Class              #24            // com/cas/lock/SynchronizedDemo2
    //   #6 = Utf8               object
    //   #7 = Utf8               Ljava/lang/Object;
    //   #8 = Utf8               <init>
    //   #9 = Utf8               ()V
    //  #10 = Utf8               Code
    //  #11 = Utf8               LineNumberTable
    //  #12 = Utf8               method1
    //  #13 = Utf8               StackMapTable
    //  #14 = Class              #24            // com/cas/lock/SynchronizedDemo2
    //  #15 = Class              #21            // java/lang/Object
    //  #16 = Class              #25            // java/lang/Throwable
    //  #17 = Utf8               method2
    //  #18 = Utf8               SourceFile
    //  #19 = Utf8               SynchronizedDemo2.java
    //  #20 = NameAndType        #8:#9          // "<init>":()V
    //  #21 = Utf8               java/lang/Object
    //  #22 = NameAndType        #6:#7          // object:Ljava/lang/Object;
    //  #23 = NameAndType        #17:#9         // method2:()V
    //  #24 = Utf8               com/cas/lock/SynchronizedDemo2
    //  #25 = Utf8               java/lang/Throwable
    //{
    //  java.lang.Object object;
    //    descriptor: Ljava/lang/Object;
    //    flags:
    //
    //  public com.cas.lock.SynchronizedDemo2();
    //    descriptor: ()V
    //    flags: ACC_PUBLIC
    //    Code:
    //      stack=3, locals=1, args_size=1
    //         0: aload_0
    //         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
    //         4: aload_0
    //         5: new           #2                  // class java/lang/Object
    //         8: dup
    //         9: invokespecial #1                  // Method java/lang/Object."<init>":()V
    //        12: putfield      #3                  // Field object:Ljava/lang/Object;
    //        15: return
    //      LineNumberTable:
    //        line 7: 0
    //        line 9: 4
    //
    //  public void method1();
    //    descriptor: ()V
    //    flags: ACC_PUBLIC
    //    Code:
    //      stack=2, locals=3, args_size=1
    //         0: aload_0
    //         1: getfield      #3                  // Field object:Ljava/lang/Object;
    //         4: dup
    //         5: astore_1
    //         6: monitorenter
    //         7: aload_1
    //         8: monitorexit
    //         9: goto          17
    //        12: astore_2
    //        13: aload_1
    //        14: monitorexit
    //        15: aload_2
    //        16: athrow
    //        17: invokestatic  #4                  // Method method2:()V
    //        20: return
    //      Exception table:
    //         from    to  target type
    //             7     9    12   any
    //            12    15    12   any
    //      LineNumberTable:
    //        line 11: 0
    //        line 13: 7
    //        line 14: 17
    //        line 15: 20
    //      StackMapTable: number_of_entries = 2
    //        frame_type = 255 /* full_frame */
    //          offset_delta = 12
    //          locals = [ class com/cas/lock/SynchronizedDemo2, class java/lang/Object ]
    //          stack = [ class java/lang/Throwable ]
    //        frame_type = 250 /* chop */
    //          offset_delta = 4
    //}
    //SourceFile: "SynchronizedDemo2.java"
}