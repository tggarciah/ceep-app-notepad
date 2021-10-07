package br.com.dtfoods.ceep.ui.activity;

import static br.com.dtfoods.ceep.ui.activity.NotasActivityConstantes.CHAVE_NOTA;
import static br.com.dtfoods.ceep.ui.activity.NotasActivityConstantes.CHAVE_POSICAO;
import static br.com.dtfoods.ceep.ui.activity.NotasActivityConstantes.CODIGO_REQUISICAO_ALTERA_NOTA;
import static br.com.dtfoods.ceep.ui.activity.NotasActivityConstantes.CODIGO_REQUISICAO_INSERE_NOTA;
import static br.com.dtfoods.ceep.ui.activity.NotasActivityConstantes.POSICAO_INVALIDA;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.dtfoods.ceep.R;
import br.com.dtfoods.ceep.dao.NotaDAO;
import br.com.dtfoods.ceep.model.Nota;
import br.com.dtfoods.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

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
      for (int i = 1; i <= 10; i++) {
         notaDAO.insere(new Nota("Título do card " + i, "Descrição do card " + i));
      }
      return notaDAO.todos();
   }

   private void configuraBotaoInsereNota() {
      TextView botaoInsereNota = findViewById(R.id.lista_notas_botao_insere_nota);
      botaoInsereNota.setOnClickListener(view -> {
         vaiParaFormularioNotaActivityInsere();
      });
   }

   private void vaiParaFormularioNotaActivityInsere() {
      Intent intent = new Intent(this, FormularioNotaActivity.class);
      startActivityForResult(intent, CODIGO_REQUISICAO_INSERE_NOTA);
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      if (ehResultadoInsereNota(requestCode, data)) {
         if (ehCodigoSucesso(resultCode)) {
            Nota nota = (Nota) (data != null ? data.getSerializableExtra(CHAVE_NOTA) : null);
            if (nota != null) {
               adiciona(nota);
            }
         }
      }
      if (ehResultadoAlteraNota(requestCode, data)) {
         if (ehCodigoSucesso(resultCode)) {
            Nota nota = (Nota) data.getSerializableExtra(CHAVE_NOTA);
            int posicao = data.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
            if (ehPosicaoValida(posicao)) {
               altera(nota, posicao);
            } else {
               Toast.makeText(this, "Ocorreu um problema na alteração da nota.", Toast.LENGTH_SHORT).show();
            }
         }
      }
      super.onActivityResult(requestCode, resultCode, data);
   }

   private void altera(Nota nota, int posicao) {
      new NotaDAO().altera(posicao, nota);
      adapter.altera(posicao, nota);
   }

   private boolean ehPosicaoValida(int posicao) {
      return posicao > POSICAO_INVALIDA;
   }

   private boolean ehResultadoAlteraNota(int requestCode, @Nullable Intent data) {
      return ehCodigoRequisicaoAlteraNota(requestCode) && temNota(data);
   }

   private boolean ehCodigoRequisicaoAlteraNota(int requestCode) {
      return requestCode == CODIGO_REQUISICAO_ALTERA_NOTA;
   }

   private void adiciona(Nota nota) {
      new NotaDAO().insere(nota);
      adapter.adiciona(nota);
   }

   private boolean ehResultadoInsereNota(int requestCode, @Nullable Intent data) {
      return ehCodigoRequisicaoInsereNota(requestCode) && temNota(data);
   }

   private boolean temNota(@Nullable Intent data) {
      return data.hasExtra(CHAVE_NOTA);
   }

   private boolean ehCodigoSucesso(int resultCode) {
      return resultCode == Activity.RESULT_OK;
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
      adapter.setOnItemClickListener(this::vaiParaFormularioNotaActivityAltera);
   }

   private void vaiParaFormularioNotaActivityAltera(Nota nota, int posicao) {
      Intent intent = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
      intent.putExtra(CHAVE_NOTA, nota);
      intent.putExtra(CHAVE_POSICAO, posicao);
      startActivityForResult(intent, CODIGO_REQUISICAO_ALTERA_NOTA);
   }
}