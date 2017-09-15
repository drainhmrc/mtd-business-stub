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

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.play.microservice.controller.BaseController
import uk.gov.hmrc.play.http.logging.MdcLoggingExecutionContext._
import play.api.mvc._

import scala.concurrent.Future

object Controller extends Controller

trait Controller extends BaseController {

  val foo :JsValue =
    Json.parse(
      s"""
         |{
         |  "identifiers":[
         |    {
         |      "name":"nino",
         |      "value":"AA123456A"
         |    }
         |  ]
         |}
     """.stripMargin)

  val identifierMapping = Map("bar" -> foo)

  def getTaxIdentifiers(token: String): Action[AnyContent] = Action.async { implicit request =>
    token match {
      case _ if identifierMapping.contains(token)=> Future.successful(Ok(identifierMapping(token).toString()))
      case _ => Future.successful(NotFound)
    }
  }
}