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

import org.scalatest.concurrent.ScalaFutures
import play.api.http.Status
import play.api.libs.json.Json
import play.api.test.FakeRequest
import uk.gov.hmrc.play.test._

class ControllerSpec extends UnitSpec with WithFakeApplication with ScalaFutures {

  implicit val materialiser = fakeApplication.materializer

  val fakeRequest = FakeRequest("GET", "/")
  val expectedResponseBody =
    Json.parse(s"""
       |{
       |  "identifiers":[
       |    {
       |      "name":"nino",
       |      "value":"AA123456A"
       |    }
       |  ]
       |}
     """.stripMargin)

  "GET /mtdfbit/[validToken]" should {
    "return 200" in {
      val result = await(Controller.getTaxIdentifiers("mtdfbit", "12345")(fakeRequest))
      status(result) shouldBe Status.OK
      bodyOf(result) shouldBe expectedResponseBody.toString()
    }
  }

  "GET /notexisting/[validToken]]" should {
    "return 404" in {
      val result = await(Controller.getTaxIdentifiers("notexisting", "12345")(fakeRequest))
      status(result) shouldBe Status.NOT_FOUND
    }
  }


}
