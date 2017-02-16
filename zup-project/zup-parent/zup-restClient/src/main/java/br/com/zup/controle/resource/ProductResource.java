package br.com.zup.controle.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.zup.model.dao.entity.Product;
import br.com.zup.negocio.IProductNegocio;

@Path("/products")
public class ProductResource extends AbstractResource{

    @Inject
    private IProductNegocio productNegocio;
    
    @POST
    @Path("/find")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> buscarPorExemplo(Product example) throws Exception {
        return productNegocio.buscarPorExemplo(example);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> listar() throws Exception {
        return productNegocio.listar();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product buscarPorId(@PathParam("id") Long id) throws Exception {
        return productNegocio.buscarPorId(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Product salvar(Product t) throws Exception {
        return productNegocio.salvarOuAtualizar(t);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product atualizar(Product t) throws Exception {
        return productNegocio.salvarOuAtualizar(t);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean remover(@PathParam("id") Long id) throws Exception {		
        return productNegocio.removerPorId(id);
    }
}
