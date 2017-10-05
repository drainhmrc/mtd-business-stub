package uk.gov.hmrc.mtdbusinessstub

import uk.gov.hmrc.play.http.HeaderCarrier
import uk.gov.hmrc.play.it._

import scala.concurrent.duration._


class MtdBusinessStubServer extends ServiceSpec {
  protected val server = new MtdBusinessStubIntegrationServer("MTD_BUSINESS_STUB_IT_TESTS")

  class MtdBusinessStubIntegrationServer(override val testName: String) extends MicroServiceEmbeddedServer {
    override protected def additionalConfig: Map[String, _] = Map(
      "Dev.auditing.consumer.baseUri.port" -> externalServicePorts("datastream")
    )

    def localResource(path: String): String = {
      s"http://localhost:$servicePort$path"
    }

    override protected val externalServices: Seq[ExternalService] = externalServiceNames.map(ExternalServiceRunner.runFromJar(_))

    override protected def startTimeout: Duration = 300 seconds
  }

  def externalServiceNames: Seq[String] = {
    Seq(
      "datastream",
      "auth",
      "entity-resolver",
      "preferences"
    )
  }
}

trait EndpointSupport {
  self: MtdBusinessStubServer =>
  implicit val hc = HeaderCarrier()

  def `/income-tax-subscription/reset-preferences` = resource("/income-tax-subscription/reset-preferences")
  def post(url: String) = WSHttp.POSTEmpty(url)
}