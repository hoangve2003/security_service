# Hướng dẫn sử dụng API
- URL POSTMAN: https://app.getpostman.com/join-team?invite_code=2b4317a2fcee8a7e6eeafff640fd026b&target_code=0dcfb949ad5a5e419cd0103ffcffce77
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
# HƯỚNG DẪN SỬ DỤNG DOCKER COMPOSE 
- cd dến file securityserviceapi nơi chứa file docker-compose.yml
- gõ lệnh cmnd docker compose up để run project và test api
