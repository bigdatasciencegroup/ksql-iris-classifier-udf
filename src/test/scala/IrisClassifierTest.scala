import ksql.irisudf.Iris
import org.scalatest._


class IrisClassifierSpec extends FlatSpec with Matchers {

  "An Iris Classifier" should "return the class Iris-setosa" in {
    val iris = new Iris
    val prediction = iris.iris(5.1, 3.5, 1.4, 0.2)
    prediction should be ("Iris-setosa")
  }

  it should "return the class Iris-versicolor" in {
    val iris = new Iris
    val prediction = iris.iris(6.2,2.2,4.5,1.5)
    prediction should be ("Iris-versicolor")
  }

  it should "return the class Iris-virginica" in {
    val iris = new Iris
    val prediction = iris.iris(6.1,2.6,5.6,1.4)
    prediction should be ("Iris-virginica")
  }
   
}