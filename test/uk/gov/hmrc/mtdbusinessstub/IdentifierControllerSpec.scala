/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.mtdbusinessstub

import akka.stream.Materializer
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play.OneAppPerSuite
import play.api.test.{FakeRequest, Helpers}
import play.api.http.Status
import play.api.test.Helpers._
import play.api.libs.json._
import uk.gov.hmrc.mtdbusinessstub.controllers.IdentifierController
import uk.gov.hmrc.play.test.UnitSpec

class IdentifierControllerSpec extends UnitSpec with OneAppPerSuite with MockitoSugar with ScalaFutures {

  implicit val materializer: Materializer = app.materializer

  "Identifier Controller" should {
    "Return Not Found for an unknown token" in new TestCase {
      val result = call(controller.getTaxIdentifiers(UKNOWN_TOKEN), FakeRequest()).futureValue
      status(result) shouldBe Status.NOT_FOUND
    }
    "Return a ok for a function" in new TestCase {
      val result = call(controller.getTaxIdentifier, FakeRequest()).futureValue
      status(result) shouldBe Status.OK
    }
    "Return the correct NINO for an known token" in new TestCase {
      val result = call(controller.getTaxIdentifiers(KNOWN_TOKEN), FakeRequest())
      status(result) shouldBe Status.OK
      val message = Json.parse(Helpers.contentAsString(result))
      message shouldBe expectedResult(KNOWN_NINO)
    }
  }


  trait TestCase {
    val UKNOWN_TOKEN = "Unknown"
    val KNOWN_TOKEN = "91abdbb1-6ad4-4419-8f33-a7ea6cf8e388"
    val KNOWN_NINO = "AA123456C"

    def expectedResult(nino: String) = Json.parse(s"""{"identifiers":[{"name":"nino","value":"$nino"}]}""")

    val controller = new IdentifierController{}
  }
}