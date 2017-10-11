# mtd-business-stub

[![Build Status](https://travis-ci.org/hmrc/mtd-business-stub.svg)](https://travis-ci.org/hmrc/mtd-business-stub) [ ![Download](https://api.bintray.com/packages/hmrc/releases/mtd-business-stub/images/download.svg) ](https://bintray.com/hmrc/releases/mtd-business-stub/_latestVersion)

This is the Service specific - Backend Stub for Making Tax Digital for Business (MTDfB).


# API


Method | Endpoint | Description
-------|----------|--------------------------------
 GET | `/income-tax-subscription/identifier-mapping/:token` | Returns the Nino associated with the token
 

### GET /income-tax-subscription/identifier-mapping/:token

Responds with:

| Status        | Description | 
| --------------| ----------- |
| 200 Ok        | If the token is known |
| 404 Not Found | If no Nino is found for the token |

 

```json
{
  "identifiers": [
    {
      "name":"nino",
      "value":"AANNNNNNA" 
    }
  ]
}
```

### Predefined Tokens

| Token         | Nino | 
| --------------| ----------- |
| 91abdbb1-6ad4-4419-8f33-a7ea6cf8e388 | AA123456C |
| 23baacf4-2bc1-1114-2d12-b5ac6cf8e312 | AA123456A |
| 44acadd2-2bc1-1114-2d12-b5ac6cf8e312 | BB123457B |
| 23baacf4-2bc1-1114-2d12-b5ac6cf8e314 | EE123456D |
| 23baacf4-2bc1-1114-2d12-b5ac6cf8e313 | CC123456C |
| 23baacf4-2bc1-1114-2d12-b5ac6cf8e315 | EE123456A |
| 23baacf4-2bc1-1114-2d12-b5ac6cf8e316 | BB123456B |
| 23baacf4-2bc1-1114-2d12-b5ac6cf8e317 | BB123456C |
| 23baacf4-2bc1-1114-2d12-b5ac6cf8e318 | BB123456D |

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html")
