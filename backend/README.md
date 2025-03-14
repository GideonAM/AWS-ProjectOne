# FILE SERVER APPLICATION

    This is a web API designed to enable a client move her business
    online to enhance the scalability of her business. This application
    provides support user login, downloading, sharing of files easily.

## Logging
    Added logging functionality to the implementation of register, forgot password
    and share file endpoints, to perform some logging into a file if a
    request is to fail during it's execution.

# Deployed link and admin credentials
* email: fileserverapplication@gmail.com
* password: 123
* [Deployed link of frontend](https://file-server-frontend.vercel.app/)

# ER diagram of database design
![ER diagram of database](https://github.com/GideonAg/File-Server-Backend/assets/99260218/0faa88f4-06ed-4b78-b7c5-dcc2a1ca4fcb)

<!-- TOC -->
* [FILE SERVER APPLICATION](#file-server-application)
  * [Logging](#logging)
  * [LOGIN](#login)
    * [Request Information](#request-information)
    * [JSON Body](#json-body)
    * [Error Responses](#error-responses)
    * [Successful Response Example](#successful-response-example)
  * [USER REGISTRATION](#user-registration)
    * [Request Information](#request-information-1)
    * [JSON Body](#json-body-1)
    * [Error Responses](#error-responses-1)
    * [Successful Response Example](#successful-response-example-1)
  * [EMAIL VERIFICATION](#email-verification)
    * [Request Information](#request-information-2)
    * [Request Param](#request-param)
    * [Successful Response Example](#successful-response-example-2)
  * [UPDATE PASSWORD](#update-password)
    * [Request Information](#request-information-3)
    * [Header](#header)
    * [Request Param](#request-param-1)
    * [JSON Body](#json-body-2)
    * [Error Responses](#error-responses-2)
    * [Successful Response Example](#successful-response-example-3)
  * [FORGOT PASSWORD](#forgot-password)
    * [Request Information](#request-information-4)
    * [JSON Body](#json-body-3)
    * [Error Responses](#error-responses-3)
    * [Successful Response Example](#successful-response-example-4)
  * [CHANGE PASSWORD](#change-password)
    * [Request Information](#request-information-5)
    * [JSON Body](#json-body-4)
    * [Error Responses](#error-responses-4)
    * [Successful Response Example](#successful-response-example-5)
* [FILE CONTROLLER ENDPOINTS](#file-controller-endpoints)
  * [UPLOAD FILES](#upload-files)
    * [Request Information](#request-information-6)
    * [Header](#header-1)
    * [Form-data Body](#form-data-body)
    * [Error Responses](#error-responses-5)
    * [Successful Response Example](#successful-response-example-6)
  * [ADMIN GET ALL FILES](#admin-get-all-files)
    * [Request Information](#request-information-7)
    * [Header](#header-2)
    * [Error Responses](#error-responses-6)
    * [Successful Response Example](#successful-response-example-7)
  * [ADMIN SEARCH FOR FILES](#admin-search-for-files)
    * [Request Information](#request-information-8)
    * [Header](#header-3)
    * [Error Responses](#error-responses-7)
    * [Successful Response Example](#successful-response-example-8)
  * [ADMIN DELETE FILE](#admin-delete-file)
    * [Request Information](#request-information-9)
    * [Header](#header-4)
    * [Error Responses](#error-responses-8)
    * [Successful Response Example](#successful-response-example-9)
  * [USER GET ALL FILES](#user-get-all-files)
    * [Request Information](#request-information-10)
    * [Header](#header-5)
    * [Error Responses](#error-responses-9)
    * [Successful Response Example](#successful-response-example-10)
  * [USER SEARCH FOR FILE](#user-search-for-file)
    * [Request Information](#request-information-11)
    * [Header](#header-6)
    * [Error Responses](#error-responses-10)
    * [Successful Response Example](#successful-response-example-11)
  * [DOWNLOAD FILE](#download-file)
    * [Request Information](#request-information-12)
    * [Header](#header-7)
    * [Path Variable](#path-variable)
    * [Error Responses](#error-responses-11)
    * [Successful Response Example](#successful-response-example-12)
  * [SEND FILE TO AN EMAIL](#send-file-to-an-email)
    * [Request Information](#request-information-13)
    * [Header](#header-8)
    * [JSON Body](#json-body-5)
    * [Error Responses](#error-responses-12)
    * [Successful Response Example](#successful-response-example-13)
<!-- TOC -->


## LOGIN

### Request Information

| Method | URL                                                      |
|--------|----------------------------------------------------------|
| POST   | https://file-server-backend-1n25.onrender.com/auth/login |

### JSON Body

| Property Name | type   | required |
|---------------|--------|----------|
| email         | String | true     |
| password      | String | true     |

### Error Responses

| Code | Message                            |
|------|------------------------------------|
| 403  | "Bad Credentials"                  |
| 403  | "User is disabled"                 |
| 400  | "Invalid email address format"     |
| 400  | "Email or password can't be blank" |
| 403  | "User Not Found"                   |

### Successful Response Example

```
{
    "userEmail": user@gmail.com,
    "userId": "userIdHere",
    "jwt": "token"
}
```

## USER REGISTRATION

### Request Information

| Method | URL                                                         |
|--------|-------------------------------------------------------------|
| POST   | https://file-server-backend-1n25.onrender.com/auth/register |

### JSON Body

| Property Name | type   | required |
|---------------|--------|----------|
| email         | String | true     |
| password      | String | true     |

### Error Responses

| Code | Message                            |
|------|------------------------------------|
| 406  | "User is already registered        |
| 400  | "Invalid email address format"     |
| 400  | "Email or password can't be blank" |
### Successful Response Example

```
  "Account verification link sent to email."
```

## EMAIL VERIFICATION

### Request Information

| Method | URL                                                                                 |
|--------|-------------------------------------------------------------------------------------|
| GET    | https://file-server-backend-1n25.onrender.com/auth/register/verify?token=someToken  |

### Request Param

| Request Param Name | type   | required |
|--------------------|--------|----------|
| token              | String | true     |

### Successful Response Example

| Code | Message                           |
|------|-----------------------------------|
| 200  | "Account verification successful" |
| 200  | "Account already verified"        |


## UPDATE PASSWORD

### Request Information

| Method | URL                                                                |
|--------|--------------------------------------------------------------------|
| POST   | https://file-server-backend-1n25.onrender.com/auth/update-password |

### Header

| Type         | Property Name    |
|--------------|------------------|
| Content-Type | application/json |

### Request Param
| Property Name | type   | required |
|---------------|--------|----------|
| token         | String | true     |

### JSON Body

| Property Name | type   | required |
|---------------|--------|----------|
| password      | String | true     |

### Error Responses

| Code | Message                                                               |
|------|-----------------------------------------------------------------------|
| 400  | "Password should contain numbers and digits only. 3 to 20 characters" |
| 404  | "Password update failed"                                              |
| 400  | "Password is required"                                                |

### Successful Response Example

```
    Password updated successfully
```

## FORGOT PASSWORD

### Request Information

| Method | URL                                                                |
|--------|--------------------------------------------------------------------|
| POST   | https://file-server-backend-1n25.onrender.com/auth/forgot-password |

### JSON Body

| Property Name | type   | required |
|---------------|--------|----------|
| email         | String | true     |

### Error Responses

| Code | Message                          |
|------|----------------------------------|
| 400  | "Please enter a valid email"     |
| 400  | "Email cannot be null or empty"  |
| 404  | "Incorrect email address"        |

### Successful Response Example

```
    Password reset link sent to email
```

## CHANGE PASSWORD

### Request Information

| Method | URL                                                                |
|--------|--------------------------------------------------------------------|
| POST   | https://file-server-backend-1n25.onrender.com/auth/change-password |

### JSON Body

| Property Name   | type   | required |
|-----------------|--------|----------|
| currentPassword | String | true     |
| newPassword     | String | true     |
| jwt             | String | true     |

### Error Responses

| Code | Message                                                               |
|------|-----------------------------------------------------------------------|
| 400  | "Password should contain numbers and digits only. 3 to 20 characters" |
| 400  | "Current password is required"                                        |
| 200  | "Incorrect current password"                                          |

### Successful Response Example
```
  Password updated successfully
```

# FILE CONTROLLER ENDPOINTS

## UPLOAD FILES

### Request Information

| Method | URL                                                       |
|--------|-----------------------------------------------------------|
| POST   | https://file-server-backend-1n25.onrender.com/file/upload |

### Header

| Type         | Property Name       |
|--------------|---------------------|
| Content-Type | multipart/form-data |
| token        | jwt                 |

### Form-data Body

| Property Name | type   | required |
|---------------|--------|----------|
| title         | String | true     |
| description   | String | true     |
| file          | file   | true     |

### Error Responses

| Code | Message                                   |
|------|-------------------------------------------| 
| 403  | "Forbidden"                               |
| 400  | "File title and description are required" |

### Successful Response Example

```
    File uploaded successfully
```

## ADMIN GET ALL FILES

### Request Information

| Method | URL                                                                |
|--------|--------------------------------------------------------------------|
| GET    | https://file-server-backend-1n25.onrender.com/file/admin/all-files |

### Header

| Type         | Property Name    |
|--------------|------------------|
| Content-Type | application/json |
| token        | jwt              |


### Error Responses

| Code | Message     |
|------|-------------| 
| 403  | "Forbidden" |

### Successful Response Example

```
[
  {
    "id": "6ccbcaa2-9194-420f-8b68-ce4ce3e651a2",
    "title": "gdg",
    "description": "d",
    "file": null,
    "numberOfDownloads": 3,
    "numberOfShares": 0,
    "fileType": null
  },
  {
    "id": "52527a09-8062-41aa-820b-36b02bf0e91b",
    "title": "Devs",
    "description": "compsa",
    "file": null,
    "numberOfDownloads": 8,
    "numberOfShares": 0,
    "fileType": null
  }
]
```

## ADMIN SEARCH FOR FILES

### Request Information

| Method | URL                                                                                 |
|--------|-------------------------------------------------------------------------------------|
| GET    | https://file-server-backend-1n25.onrender.com/file/admin/search-for-file/{fileName} |

### Header

| Type         | Property Name    |
|--------------|------------------|
| Content-Type | application/json |
| token        | jwt              |


### Error Responses

| Code | Message     |
|------|-------------| 
| 403  | "Forbidden" |

### Successful Response Example
```
[
  {
    "id": "d79c6f74-d88d-4804-9cd9-af63daeaae87",
    "title": "ppt",
    "description": "ppt des",
    "file": null,
    "numberOfDownloads": 1,
    "numberOfShares": 0,
    "fileType": null
  }
]
```

## ADMIN DELETE FILE

### Request Information

| Method | URL                                                                |
|--------|--------------------------------------------------------------------|
| GET    | https://file-server-backend-1n25.onrender.com/file/delete/{fileId} |

### Header

| Type         | Property Name    |
|--------------|------------------|
| Content-Type | application/json |
| token        | jwt              |


### Error Responses

| Code | Message                 |
|------|-------------------------| 
| 403  | "Forbidden"             |
| 404  | "Failed to delete file" |

### Successful Response Example
```
    File deleted successfully
```

## USER GET ALL FILES

### Request Information

| Method | URL                                                               |
|--------|-------------------------------------------------------------------|
| GET    | https://file-server-backend-1n25.onrender.com/file/user/all-files |

### Header

| Type         | Property Name        |
|--------------|----------------------|
| Content-Type | application/json     |
| token        | Authentication Token |


### Error Responses

| Code | Message     |
|------|-------------| 
| 403  | "Forbidden" |

### Successful Response Example
```
[
  {
    "id": "6ccbcaa2-9194-420f-8b68-ce4ce3e651a2",
    "title": "gdg",
    "description": "d"
  },
  {
    "id": "52527a09-8062-41aa-820b-36b02bf0e91b",
    "title": "Devs",
    "description": "compsa"
  }
]
```

## USER SEARCH FOR FILE

### Request Information

| Method | URL                                                                                |
|--------|------------------------------------------------------------------------------------|
| GET    | https://file-server-backend-1n25.onrender.com/file/user/search-for-file/{fileName} |

### Header

| Type         | Property Name        |
|--------------|----------------------|
| Content-Type | application/json     |
| token        | Authentication Token |


### Error Responses

| Code | Message     |
|------|-------------| 
| 403  | "Forbidden" |


### Successful Response Example

```
[
  {
    "id": "d79c6f74-d88d-4804-9cd9-af63daeaae87",
    "title": "ppt",
    "description": "ppt des",
    "file": null,
    "numberOfDownloads": 0,
    "numberOfShares": 0,
    "fileType": null
  }
]
```

## DOWNLOAD FILE

### Request Information

| Method | URL                                                              |
|--------|------------------------------------------------------------------|
| GET    | https://file-server-backend-1n25.onrender.com/file/download/{id} |

### Header

| Type         | Property Name |
|--------------|---------------|
| token        | jwt           |

### Path Variable

| Property Name | type   | required |
|---------------|--------|----------|
| id            | String | true     |

### Error Responses

| Code | Message          |
|------|------------------| 
| 404  | "File Not Found" |

### Successful Response Example
```
returns a byte array of the file
```

## SEND FILE TO AN EMAIL

### Request Information

| Method | URL                                                      |
|--------|----------------------------------------------------------|
| POST   | https://file-server-backend-1n25.onrender.com/file/share |

### Header

| Type         | Property Name    |
|--------------|------------------|
| Content-Type | application/json |
| token        | jwt              |

### JSON Body

| Property Name | type   | required |
|---------------|--------|----------|
| receiverEmail | String | true     |
| fileId        | String | true     |

### Error Responses

| Code | Message                         |
|------|---------------------------------|
| 400  | "File ID is required"           |
| 400  | "Please enter a valid email"    |
| 400  | "Email cannot be null or empty" |
| 404  | "File not found"                |

### Successful Response Example
```
File sent to {receiverEmail} successfully
```
