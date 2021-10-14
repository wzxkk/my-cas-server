package org.wzx.mycasserver.util;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * @description:
 * @author: 鱼头(韦忠幸)
 * @time: 2021-10-11 09:56
 */
public class CreateClass {
    public static void myclass() {
        try {
            StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("package org.yutou.auth.srctest.srcone.controller;\n");
//            stringBuffer.append("package org.wzx.mycasclient.controller;\n");
            stringBuffer.append("import org.springframework.web.bind.annotation.GetMapping;\n");
            stringBuffer.append("import org.springframework.web.bind.annotation.RestController;\n");
            stringBuffer.append("import org.springframework.stereotype.Component;\n");
            stringBuffer.append("@Component\n");
            stringBuffer.append("@RestController\n");
            stringBuffer.append("public class TTT {\n");
            stringBuffer.append("@GetMapping(\"ttt\")\n");
            stringBuffer.append("public void ttt() {\n");
            stringBuffer.append("System.out.println(\"成功啦！！！！！！！！！！！！！！！！！！！！！！！！！！\");\n");
            stringBuffer.append("}\n");
            stringBuffer.append("}\n");
            Class<?> cl = createClass(stringBuffer.toString());
            //***************************************************************************
//
//            Object object = cl.getDeclaredConstructor().newInstance();
//
//            // 注入 bean 容器的代码 : 容器中是存在这个 bean 对象的,但是Controller却没有访问到.
//            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(object.getClass());
//            ConfigurableApplicationContext context = WebInterceptor.getContext();
//            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
//            beanFactory.registerBeanDefinition("testController",builder.getBeanDefinition());
//
//            RequestMappingHandlerMapping mappingHandlerMapping = context.getBean(RequestMappingHandlerMapping.class);
//            Object oj = context.getBean("testController");
//            Map<Method, RequestMappingInfo> methods = MethodIntrospector.selectMethods(oj.getClass(),(MethodIntrospector.MetadataLookup<RequestMappingInfo>) method ->{
//                try{
//                    RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
//                    RequestMappingInfo.Builder mappping = RequestMappingInfo.paths(requestMapping.path())
//                            .methods(requestMapping.method())
//                            .params(requestMapping.params())
//                            .headers(requestMapping.headers())
//                            .consumes(requestMapping.consumes())
//                            .produces(requestMapping.produces())
//                            .mappingName(requestMapping.name());
//                    return mappping.build();
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//                return null;
//            });
//
//            Method rmhmMethod = mappingHandlerMapping.getClass()
//                    .getDeclaredMethod("registerHandlerMethod",new Class[]{Object.class, Method.class, Object.class});
//
//            rmhmMethod.setAccessible(true);
//            methods.forEach((method,mapping) -> {
//                try{
//                    rmhmMethod.invoke(mappingHandlerMapping,new Object[]{oj,method,mapping} );
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            });
            //***************************************************************************
            System.out.println(cl.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param code 源代码内容
     * @return
     * @throws Exception
     * @方法说明: 动态生成java类, 不生成中间文件，前提是环境必须是满足这个新类的要求
     * @创建时间: 2019年9月19日 下午4:45:48
     * @创建者: 韦忠幸
     * @修改时间:
     * @修改人员:
     * @修改说明:
     */
    private static Class<?> createClass(String code) throws ClassNotFoundException, URISyntaxException {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);
        ClassJavaFileManager classJavaFileManager = new ClassJavaFileManager(standardFileManager);
        String className = null;
        String[] cs = code.split("\\s+");
        String packagePath = "";
        for (int i = 0; i < cs.length - 1; i++) {
            if (cs[i].equals("package")) {
                packagePath = cs[i + 1].replace(";", ".");
            }
            if (cs[i].equals("class")) {
                className = cs[i + 1];
                if (className.contains("{")) {
                    className = className.substring(0, className.indexOf("{"));
                }
                break;
            }
        }
        StringObject stringObject = new StringObject(new URI(className + ".java"), JavaFileObject.Kind.SOURCE, code);
        JavaCompiler.CompilationTask task = compiler.getTask(null, classJavaFileManager, null, null, null,
                Arrays.asList(stringObject));
        if (task.call()) {
            ClassJavaFileObject javaFileObject = classJavaFileManager.getClassJavaFileObject();
            ClassLoader classLoader = new MyClassLoader(javaFileObject);
            return classLoader.loadClass(packagePath + className);
        }
        return null;
    }

    /**
     * 自定义fileManager
     */
    private static class ClassJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {
        private ClassJavaFileObject classJavaFileObject;

        public ClassJavaFileManager(JavaFileManager fileManager) {
            super(fileManager);
        }

        public ClassJavaFileObject getClassJavaFileObject() {
            return classJavaFileObject;
        }

        /**
         * 这个方法一定要自定义
         */
        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind,
                                                   FileObject sibling) throws IOException {
            return (classJavaFileObject = new ClassJavaFileObject(className, kind));
        }
    }

    /**
     * 存储源文件
     */
    private static class StringObject extends SimpleJavaFileObject {
        private String content;

        public StringObject(URI uri, Kind kind, String content) {
            super(uri, kind);
            this.content = content;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return this.content;
        }
    }

    /**
     * class文件（不需要存到文件中）
     */
    private static class ClassJavaFileObject extends SimpleJavaFileObject {
        ByteArrayOutputStream outputStream;

        public ClassJavaFileObject(String className, Kind kind) {
            super(URI.create(className + kind.extension), kind);
            this.outputStream = new ByteArrayOutputStream();
        } // 这个也要实现

        @Override
        public OutputStream openOutputStream() throws IOException {
            return this.outputStream;
        }

        public byte[] getBytes() {
            return this.outputStream.toByteArray();
        }
    }

    /**
     * 自定义classloader
     */
    private static class MyClassLoader extends ClassLoader {
        private ClassJavaFileObject stringObject;

        public MyClassLoader(ClassJavaFileObject stringObject) {
            this.stringObject = stringObject;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] bytes = this.stringObject.getBytes();
            return defineClass(name, bytes, 0, bytes.length);
        }
    }
}
