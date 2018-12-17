package com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model;

import java.util.List;

public class PesertaList {
    public List<PesertaModel> getValue() {
        return value;
    }

    public void setValue(List<PesertaModel> value) {
        this.value = value;
    }

    public List<PesertaModel> value;

    public PesertaList(List<PesertaModel> value) {
        this.value = value;
    }
}
