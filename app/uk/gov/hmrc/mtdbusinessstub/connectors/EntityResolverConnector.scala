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

package uk.gov.hmrc.mtdbusinessstub.connectors

import play.api.http.Status
import play.api.mvc.{Result, Results}
import uk.gov.hmrc.mtdbusinessstub.model.Identifier
import uk.gov.hmrc.play.http.{HeaderCarrier, HttpDelete}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait EntityResolverConnector extends Status {

  val externalServiceName = "entity-resolver"

  def http: HttpDelete

  def serviceUrl: String

  def url(path: String) = s"$serviceUrl$path"

  def deleteNino(nino: Identifier)(implicit headerCarrier: HeaderCarrier): Future[Result] = {
    val callingUrl = url(s"/entity-resolver-admin/paye/${nino.value}")
    http.DELETE(callingUrl).map(_.status).map {
      case NO_CONTENT => Results.Ok
      case _ => Results.BadRequest
    }
  }
}
