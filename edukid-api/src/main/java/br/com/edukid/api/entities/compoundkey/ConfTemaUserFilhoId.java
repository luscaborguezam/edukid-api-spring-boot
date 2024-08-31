package br.com.edukid.api.entities.compoundkey;

import java.io.Serializable;
import java.util.Objects;

public class ConfTemaUserFilhoId implements Serializable {

    private Integer idUserChild;
    private Integer idTheme;

    // Construtores, equals e hashCode
    public ConfTemaUserFilhoId() {
    }

    public ConfTemaUserFilhoId(Integer idUserChild, Integer idTheme) {
        this.idUserChild = idUserChild;
        this.idTheme = idTheme;
    }

    public Integer getIdUserChild() {
        return idUserChild;
    }

    public void setIdUserChild(Integer idUserChild) {
        this.idUserChild = idUserChild;
    }

    public Integer getIdTheme() {
        return idTheme;
    }

    public void setIdTheme(Integer idTheme) {
        this.idTheme = idTheme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfTemaUserFilhoId that = (ConfTemaUserFilhoId) o;
        return Objects.equals(idUserChild, that.idUserChild) &&
                Objects.equals(idTheme, that.idTheme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUserChild, idTheme);
    }
}
