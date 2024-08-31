package br.com.edukid.api.entities.compoundkey;

import java.io.Serializable;
import java.util.Objects;

public class ConfMateriaUserFilhoId implements Serializable {

    private Integer idUserChild;
    private Integer idSubject;

    // Construtores, equals e hashCode
    public ConfMateriaUserFilhoId() {
    }

    public ConfMateriaUserFilhoId(Integer idUserChild, Integer idSubject) {
        this.idUserChild = idUserChild;
        this.idSubject = idSubject;
    }

    public Integer getIdUserChild() {
        return idUserChild;
    }

    public void setIdUserChild(Integer idUserChild) {
        this.idUserChild = idUserChild;
    }

    public Integer getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(Integer idSubject) {
        this.idSubject = idSubject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfMateriaUserFilhoId that = (ConfMateriaUserFilhoId) o;
        return Objects.equals(idUserChild, that.idUserChild) &&
                Objects.equals(idSubject, that.idSubject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUserChild, idSubject);
    }
}