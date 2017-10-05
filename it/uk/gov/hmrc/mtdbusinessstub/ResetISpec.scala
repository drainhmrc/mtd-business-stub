package uk.gov.hmrc.mtdbusinessstub

import play.api.http.Status._

class ResetISpec extends MtdBusinessStubServer with EndpointSupport {
  "mtd" should {
    "fail" in {
      post(`/income-tax-subscription/reset-preferences`).status shouldBe OK
    }
  }
}
