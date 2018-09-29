1.登录拦截
在SessionConfiguration中  registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**"); 实现拦截