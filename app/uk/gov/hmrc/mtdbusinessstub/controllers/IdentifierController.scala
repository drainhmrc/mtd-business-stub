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

import play.api.libs.json.Json
import play.api.mvc.{Action, Result}
import uk.gov.hmrc.mtdbusinessstub.WSHttp
import uk.gov.hmrc.mtdbusinessstub.connectors.EntityResolverConnector
import uk.gov.hmrc.play.microservice.controller.BaseController
import uk.gov.hmrc.play.http.logging.MdcLoggingExecutionContext._
import uk.gov.hmrc.mtdbusinessstub.model._
import uk.gov.hmrc.play.config.ServicesConfig
import uk.gov.hmrc.play.http.HttpDelete

import scala.concurrent.{Future, Promise}

object IdentifierController extends IdentifierController with ServicesConfig {
  override def entityResolverConnector: EntityResolverConnector = new EntityResolverConnector {
    override def http: HttpDelete = WSHttp
    override def serviceUrl: String = baseUrl("entity-resolver")
  }
}

trait IdentifierController extends BaseController {
  this: ServicesConfig =>

  def entityResolverConnector: EntityResolverConnector

  implicit val identifierFormat = Json.format[Identifier]
  implicit val identifiersFormat = Json.format[Identifiers]


  def getTaxIdentifiers(token: String) = Action.async { implicit request =>

    IdentifierMapping.getTaxIdentifiers(token) match {
      case Some(identifier) => Future.successful(Ok(Json.toJson(identifier)))
      case _ => Future.successful(NotFound)
    }
  }

  def resetAllPreferences() = Action.async { implicit request =>
    val defaultValue = Promise[Result]()
    defaultValue.success(Ok)

    IdentifierMapping.identifierMappings.map {
      case (_, nino) => entityResolverConnector.deleteNino(nino)
    }.foldRight(defaultValue.future) {
      case (result, _) => result
    }
  }
}