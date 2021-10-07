package br.com.dtfoods.ceep.ui.activity;

import static br.com.dtfoods.ceep.ui.activity.NotasActivityConstantes.CHAVE_NOTA;
import static br.com.dtfoods.ceep.ui.activity.NotasActivityConstantes.CODIGO_REQUISICAO_INSERE_NOTA;
import static br.com.dtfoods.ceep.ui.activity.NotasActivityConstantes.CODIGO_RESULTADO_NOTA_CRIADA;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.dtfoods.ceep.R;
import br.com.dtfoods.ceep.dao.NotaDAO;
import br.com.dtfoods.ceep.model.Nota;
import br.com.dtfoods.ceep.ui.recyclerview.ListaNotasAdapter;

public class ListaNotasActivity extends AppCompatActivity {

   private ListaNotasAdapter adapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_lista_notas);
      setTitle("Ceep");
      List<Nota> todasNotas = pegaTodasAsNotas();
      configuraRecyclerView(todasNotas);
      configuraBotaoInsereNota();
   }

   private List<Nota> pegaTodasAsNotas() {
      NotaDAO notaDAO = new NotaDAO();
      return notaDAO.todos();
   }

   private void configuraBotaoInsereNota() {
      TextView botaoInsereNota = findViewById(R.id.lista_notas_botao_insere_nota);
      botaoInsereNota.setOnClickListener(view -> {
         vaiParaFormularioNotaActivity();
      });
   }

   private void vaiParaFormularioNotaActivity() {
      Intent intent = new Intent(this, FormularioNotaActivity.class);
      startActivityForResult(intent, CODIGO_REQUISICAO_INSERE_NOTA);
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      if (ehResultadoComNota(requestCode, resultCode, data)) {
         Nota nota = (Nota) (data != null ? data.getSerializableExtra(CHAVE_NOTA) : null);
         if (nota != null) {
            adiciona(nota);
         }
      }
      super.onActivityResult(requestCode, resultCode, data);
   }

   private void adiciona(Nota nota) {
      new NotaDAO().insere(nota);
      adapter.adiciona(nota);
   }

   private boolean ehResultadoComNota(int requestCode, int resultCode, @Nullable Intent data) {
      return ehCodigoRequisicaoInsereNota(requestCode) && ehCodigoResultadoNotaCriada(resultCode) && temNota(data);
   }

   private boolean temNota(@Nullable Intent data) {
      return data.hasExtra(CHAVE_NOTA);
   }

   private boolean ehCodigoResultadoNotaCriada(int resultCode) {
      return resultCode == CODIGO_RESULTADO_NOTA_CRIADA;
   }

   private boolean ehCodigoRequisicaoInsereNota(int requestCode) {
      return requestCode == CODIGO_REQUISICAO_INSERE_NOTA;
   }

   private void configuraRecyclerView(List<Nota> todasNotas) {
      RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);
      configuraAdapter(todasNotas, listaNotas);
   }

   private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
      adapter = new ListaNotasAdapter(this, todasNotas);
      listaNotas.setAdapter(adapter);
   }
}