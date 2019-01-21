# request
封装了一个简单的网络请求框架，可以一键切换网络请求框架

### 全局使用

```java
OkGo.getInstance().init(httpEngine/*你自己项目里面的网络请求引擎，实现HttpEngine接口*/);
//get请求
//OkGo.getInstance().get(...);
```

#### 配置全局的错误处理

```java
/*建议放在application中区分处理*/
CommonResultHandler.getInstance().addExceptionListener(new ExceptionListener() {
            @Override
            public void onError(int code, String errorMsg) {
                switch (code) {
                    case CommonResultHandler.CODE_FAIL://也可以是自定义的code类型
                         //TODO 对error的code进行操作处理
                        break;
                    case CommonResultHandler.CODE_DATA_NULL:
                         //TODO 对error的code进行操作处理
                        break;
                    default:
                        break;
                }
            }
        });
```





##### 注意，封装自己的网络请求时，二次处理服务器响应值，这样方便我们项目的一个整体拦截

以okhttp为例

```java
  	@Override
            public void onFailure(Call call, IOException e) {
                NetRuntimeException exception = ExceptionHandler.getInstance().handle(e);
                LogUtils.e(exception.getMessage());
                callback.onError(CommonResultHandler.CODE_FAIL, exception.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //这里的code只是反应服务器响应成功
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    CommonResultHandler.getInstance().handle(responseBody, callback);
                } else {
                    callback.onError(response.code(), "服务器响应异常！");
                }
            }
```

