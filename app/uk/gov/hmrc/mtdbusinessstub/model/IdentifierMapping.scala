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

package uk.gov.hmrc.mtdbusinessstub.model


object IdentifierMapping {


  val identifierMappings = Map(
    "91abdbb1-6ad4-4419-8f33-a7ea6cf8e388" -> Identifier("nino", "AA123456C"),
    "23baacf4-2bc1-1114-2d12-b5ac6cf8e312" -> Identifier("nino", "AA123456A"),
    "44acadd2-2bc1-1114-2d12-b5ac6cf8e312" -> Identifier("nino", "BB123456B"),
    "23baacf4-2bc1-1114-2d12-b5ac6cf8e313" -> Identifier("nino", "CC123456C"),
    "23baacf4-2bc1-1114-2d12-b5ac6cf8e314" -> Identifier("nino", "EE123456D"),
    "23baacf4-2bc1-1114-2d12-b5ac6cf8e315" -> Identifier("nino", "EE123456A"),
    "23baacf4-2bc1-1114-2d12-b5ac6cf8e316" -> Identifier("nino", "BB123456B"),
    "23baacf4-2bc1-1114-2d12-b5ac6cf8e317" -> Identifier("nino", "BB123456C"),
    "23baacf4-2bc1-1114-2d12-b5ac6cf8e318" -> Identifier("nino", "BB123456D")
  )

  def getTaxIdentifiers(token: String) = {
    identifierMappings.get(token) match {
      case Some(identifier) => Some(Identifiers(Seq(identifier)))
      case _ => None
    }
  }
}

case class Identifier(name: String, value: String)
case class Identifiers(identifiers: Seq[Identifier])