package uk.gov.hmrc.mtdbusinessstub.helpers

import play.api.inject.guice.GuiceApplicationBuilder

object ConfigHelper {

  private def additionalConfig = Map(
    "govuk-tax.Test.assets.url" -> "fake/url",
    "govuk-tax.Test.assets.version" -> "54321",
    "govuk-tax.Test.google-analytics.host" -> "host",
    "govuk-tax.Test.google-analytics.token" -> "aToken"
  )

  lazy val fakeApp = new GuiceApplicationBuilder().configure(ConfigHelper.additionalConfig).build()
}
