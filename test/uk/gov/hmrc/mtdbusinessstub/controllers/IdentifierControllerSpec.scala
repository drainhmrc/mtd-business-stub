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

package uk.gov.hmrc.mtdbusinessstub.controllers

import akka.stream.Materializer
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play.OneAppPerSuite
import play.api.http.Status
import play.api.libs.json._
import play.api.mvc.Result
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Helpers}
import uk.gov.hmrc.mtdbusinessstub.model.IdentifierMapping
import uk.gov.hmrc.play.config.ServicesConfig
import uk.gov.hmrc.play.test.UnitSpec

class IdentifierControllerSpec extends UnitSpec with OneAppPerSuite with MockitoSugar with ScalaFutures {

  implicit val materializer: Materializer = app.materializer

  "Identifier Controller" should {
    "Return Not Found for an unknown token" in new TestCase {
      val result: Result = call(controller.getTaxIdentifiers(UKNOWN_TOKEN), FakeRequest()).futureValue
      status(result) shouldBe Status.NOT_FOUND
    }

    "Return the correct NINO for an known token" in new TestCase {
      val result : Result = call(controller.getTaxIdentifiers(KNOWN_TOKEN), FakeRequest())
      status(result) shouldBe Status.OK
      val message: JsValue = Json.parse(Helpers.contentAsString(result))
      message shouldBe expectedResult(KNOWN_NINO)
    }

    "Return sucessfully for all known tokens" in new TestCase {
      IdentifierMapping.identifierMappings.foreach{
        case (identifier, _) =>
          val result = call(controller.getTaxIdentifiers(identifier), FakeRequest())
          status(result) shouldBe Status.OK
      }
    }
  }


  trait TestCase {
    val UKNOWN_TOKEN = "Unknown"
    val KNOWN_TOKEN = "91abdbb1-6ad4-4419-8f33-a7ea6cf8e388"
    val KNOWN_NINO = "AA123456C"

    def expectedResult(nino: String): JsValue = Json.parse(s"""{"identifiers":[{"name":"nino","value":"$nino"}]}""")

    val controller = new IdentifierController with ServicesConfig
  }
}