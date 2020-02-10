package ksql.irisudf

import io.confluent.ksql.function.udf.Udf
import io.confluent.ksql.function.udf.UdfDescription
import ml.combust.mleap.core.types.{StructType, StructField, ScalarType}
import ml.combust.bundle.BundleFile
import ml.combust.mleap.runtime.MleapSupport
import ml.combust.mleap.runtime.frame.{DefaultLeapFrame, Row}
import resource.managed


@UdfDescription(name = "iris", description = "classifies iris data")
class Iris extends MleapSupport {

    val schema: StructType = StructType(
        StructField("sepal_length_cm", ScalarType.Double),
        StructField("sepal_width_cm", ScalarType.Double),
        StructField("petal_length_cm", ScalarType.Double),
        StructField("petal_width_cm", ScalarType.Double),
        StructField("class", ScalarType.String)
    ).get

    val modelpath = getClass.getResource("/model").getPath

    val pipeline = (
        for(bundle <- managed(BundleFile(s"jar:$modelpath"))) yield {
            bundle.loadMleapBundle().get
        }
    ).tried.get.root

    @Udf(description = "classify iris data")
    def iris(
        sepal_length_cm: Double, sepal_width_cm: Double,
        petal_length_cm: Double, petal_width_cm: Double
    ): String = {
            pipeline.transform(
                DefaultLeapFrame(
                    schema, 
                    Seq(Row(sepal_length_cm, sepal_width_cm, petal_length_cm, petal_width_cm, "Iris-setosa"))
                )
            ).get.select("predictedLabel").get.dataset.map(_.getString(0)).head
    }

}