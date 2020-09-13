import scala.io.StdIn
import scala.util.control.Breaks

object TestScala {
  def main(args: Array[String]): Unit = {

    /**
     * 定义变量
     */
    var varable1: String = "HelloWorld"
    println(varable1)

    var varable2: Int = 3
    var varable3: Char = '3'
    var varable4: Double = 3.0
    var varable5: Float = 3.0f
    var isResult: Boolean = false
    var isResult2: Boolean = true

    //强类型转换
    var num: Int = 2.7.toInt

    println(s"variables $varable2  $varable3")

    // 建议，在开发中需要高精度小数时，请选择Double
    var n7 = 2.2345678912f
    var n8 = 2.2345678912

    //Scala的整型，默认为Int型，声明Long型，须后加‘l’或‘L’
    var n6 = 9223372036854775807L


    /**
     * 标准输入
     */
    var age = StdIn.readShort()
    var name = StdIn.readLine()
    var salary = StdIn.readDouble()


    /* Scala运算符本质
     在Scala中其实是没有运算符的，所有运算符都是方法。
     1）当调用对象的方法时，点.可以省略
     2）如果函数参数只有一个，或者没有参数，()可以省略*/

    // 标准的加法运算
    val i: Int = 1.+(1)

    // （1）当调用对象的方法时，.可以省略
    val j: Int = 1 + (1)

    // （2）如果函数参数只有一个，或者没有参数，()可以省略
    val k: Int = 1 + 1

    println(1.toString())
    println(1 toString())
    println(1 toString)


    var age1 = StdIn.readInt()

    val res :String = if (age1 < 18){
      "童年"
    }else if(age1>=18 && age1<30){
      "中年"
    }else{
      "老年"
    }
    println(res)

    /**
     * 循环结构
     */
    for(i <- 4 to 7) {
      //前后闭合循环
      println(i)
    }

    for(j <- 7 until 9) {
      //前闭后开
      println(j)
    }

    //循环守卫
    for(m <- 3 to 8 if i != 2) {
      println(m)
    }
    for (i2 <- 1 to 10 by 2) {
      println("i=" + i2)
    }
    //嵌套循环

    for(i3 <- 2 to 7; j3 <- 3 to 8 ) {
      println(i3,j3)
    }
    //引入变量
    for(i <- 1 to 3; j = 4 - i) {
      println("i=" + i + " j=" + j)
    }
    //当for推导式仅包含单一表达式时使用圆括号，当包含多个表达式时，一般每行一个表达式，并用花括号代替圆括号，如下
    for {
      i <- 1 to 3
      j = 4 - i
    } {
      println("i=" + i + " j=" + j)
    }
    //倒序循环
    for(i <- 1 to 10 reverse){
      println(i)
    }

    /**
     * 循环中断
     *
     */
    Breaks.breakable(
      for (elem <- 1 to 10) {
        println(elem)
        if (elem == 5) Breaks.break()
      }
    )

  }



  /**
   * 函数式编程
   *
   */
  def function0(args : String): Unit = {
    println("Hello!")
  }
  //函数嵌套

  // 函数1：无参，无返回值
  def test1(): Unit ={
    println("无参，无返回值")
  }


  // 函数2：无参，有返回值
  def test2():String={
    return "无参，有返回值"
  }


  // 函数3：有参，无返回值
  def test3(s:String):Unit={
    println(s)
  }


  // 函数4：有参，有返回值
  def test4(s:String):String={
    return s+"有参，有返回值"
  }



  // 函数5：多参，无返回值
  def test5(name:String, age:Int):Unit={
    println(s"$name, $age")
  }

  //可变参数函数
  //如果参数列表中存在多个参数，那么可变参数一般放置在最后
  def test( s : String* ): Unit = {
    println(s)
  }
  //参数默认值
  //如果参数传递了值，那么会覆盖默认值
  def test3( name : String, age : Int = 30 ): Unit = {
    println(s"$name, $age")
  }







}
