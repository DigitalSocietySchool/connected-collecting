/**
 * Created on 08/11/2012 by davi.alves for subscription-core-persistence
 *
 * Copyright M4U Soluções S.A.
 */
package com.medialab.persistence;

import net.sf.ehcache.CacheManager;

/**
 * <p>
 * <b>Descricao da Classe:</b><br> 
 * TODO Explicar detalhadamente propósito da classe.
 * <p>
 * <b>Forma de Uso:</b><br>
 * TODO Texto explicativo. 
 * <p>
 * <b>Codigo Exemplo: </b><br>
 * <pre>
 * TODO Colocar trecho de codigo que mostre como utilizar a classe
 * </pre>
 *
 * @author Davi Alves
 * @since 08/11/2012
 */
public class CacheService
{
    /**
     * Construtor Padrao da Classe. 
     */
    public CacheService()
    {
    }

    public void clearCache(String key)
    {
        CacheManager cacheManager = CacheManager.getInstance();
        cacheManager.getEhcache(key).removeAll();
    }

    public void clearAllCaches()
    {
        String[] caches = { "com.medialab.persistence.entity.Album",
                "com.medialab.persistence.entity.Content",
                "com.medialab.persistence.entity.Player",
                "com.medialab.persistence.entity.Sticker",
                "com.medialab.persistence.entity.Rank" };
        CacheManager cacheManager = CacheManager.getInstance();
        for (String cache : caches)
        {
            cacheManager.getEhcache(cache).removeAll();
        }
    }

}
