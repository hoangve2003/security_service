# Hướng dẫn sử dụng API

## Đăng ký (Sign Up)

- Method: POST
- URL: http://localhost:8081/api/v1/users/signUp
- Request Body:
  ```json
  {
    "name": "Tên của bạn",
    "email": "email@example.com",
    "password": "Mật khẩu của bạn",
    "roles": "Quyền của tài khoản (nếu có)"
  }
- Response: Kết quả của việc đăng ký
## Đăng nhập (Login)
- Method: POST
- URL: http://localhost:8081/api/v1/users/login
- Request Body:
```json
{
  "username": "Tên người dùng hoặc email",
  "password": "Mật khẩu"
}
```
- Response:
```json
{
  "accessToken": "Bearer jwt_access_token",
  "token": "Refresh_token"
}
```
- (accessToken là chuỗi Bearer jwt_access_token, token là Refresh_token)
## Làm mới Token (Refresh Token)
Method: POST
URL: http://localhost:8081/api/v1/users/refreshToken
Request Body:
```json
{
  "token": "Mã token refresh ở phần đăng nhập"
}
```
## Lấy thông tin người dùng (Get User)
- Method: GET
- URL: http://localhost:8081/api/v1/users/get
- Request Params:
username: Tên người dùng

- Lưu ý: Đăng nhập trước khi gửi yêu cầu.
