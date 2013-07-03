/**
 * Created on 11/12/2012 by davi.alves for subscription-core-server
 *
 * Copyright M4U Soluções S.A.
 */
package com.medialab.api.resource;

import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.medialab.beans.ConnectorConstants;
import com.medialab.persistence.CacheService;


/**
 * <p>
 * <b>Descricao da Classe:</b><br>
 * TODO Explicar detalhadamente propósito da classe.
 * <p>
 * <b>Forma de Uso:</b><br>
 * TODO Texto explicativo.
 * <p>
 * <b>Codigo Exemplo: </b><br>
 * 
 * <pre>
 * TODO Colocar trecho de codigo que mostre como utilizar a classe
 * </pre>
 * 
 * @author Davi Alves
 * @since 11/12/2012
 */
@Path(ConnectorConstants.TOOLS_RESOURCE_NAME)
public class ToolsResource
{
    @GET
    @Produces( { MediaType.TEXT_HTML })
    public Response getTools()
    {
        InputStream is = getClass().getResourceAsStream("/cache-tools.html");
        return Response.ok(is, MediaType.TEXT_HTML).build();
    }

    @GET
    @Path(ConnectorConstants.CACHE_CLEAR_ALL_NAME)
    public Response cacheClearAll()
    {
        new CacheService().clearAllCaches();
        return createOk();
    }

    @GET
    @Path(ConnectorConstants.CACHE_CLEAR_NAME)
    public Response cacheClear(@QueryParam(ConnectorConstants.CACHE_CLEAR_KEY_NAME)
    String cacheKey)
    {
        new CacheService().clearCache(cacheKey);
        return createOk();
    }

    private Response createOk()
    {
        return Response.ok().build();
    }
}
