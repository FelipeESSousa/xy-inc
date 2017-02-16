package br.com.zup.web.heimdall.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Permissao {

    private String permissao;

    public Permissao() {
    }
    
	public Permissao(String permissao) {
		super();
		this.permissao = permissao;
	}

	public String getPermissao() {
		return permissao;
	}

	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((permissao == null) ? 0 : permissao.hashCode());
        return result;
    }
	
	@Override
    public boolean equals(Object obj) {
	    Permissao other = (Permissao) obj;
        return other.getPermissao().startsWith(this.permissao);
    }
}
