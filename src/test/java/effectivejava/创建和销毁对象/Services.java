package effectivejava.创建和销毁对象;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 服务接口
interface Service
{

}

// 服务提供者接口
interface Provider
{
    Service newService();
}
public class Services
{
    private Services()
    {
    };

    private static final Map<String, Provider> providers = new ConcurrentHashMap<>();

    public static final String DEFAULT_PROVIDER_NAME = "<def>";

    // 提供者注册API
    public static void registerDefaultProvider(Provider p)
    {
        registerDefaultProvider(DEFAULT_PROVIDER_NAME, p);
    }

    public static void registerDefaultProvider(String name, Provider p)
    {
        providers.put(name, p);
    }

    // 服务访问API
    public static Service newInstance()
    {
        return newInstance(DEFAULT_PROVIDER_NAME);
    }

    public static Service newInstance(String name)
    {
        Provider p = providers.get(name);
        if (p == null)
        {
            throw new IllegalArgumentException("No provider registered with name:" + name);
        }
        return p.newService();
    }
}
