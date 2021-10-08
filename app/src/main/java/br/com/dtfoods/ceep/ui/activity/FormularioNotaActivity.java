package br.com.dtfoods.ceep.ui.activity;

import static br.com.dtfoods.ceep.ui.activity.NotasActivityConstantes.CHAVE_NOTA;
import static br.com.dtfoods.ceep.ui.activity.NotasActivityConstantes.CHAVE_POSICAO;
import static br.com.dtfoods.ceep.ui.activity.NotasActivityConstantes.POSICAO_INVALIDA;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.dtfoods.ceep.R;
import br.com.dtfoods.ceep.model.Nota;

public class FormularioNotaActivity extends AppCompatActivity {

   public static final String TITULO_APPBAR_ALTERA = "Altera a nota";
   public static final String TITULO_APPBAR_NOVO = "Insere a nota";

   private int posicao = POSICAO_INVALIDA;
   private EditText campoTitulo;
   private EditText campoDescricao;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_formulario_nota);

      inicializaCampos();

      Intent intent = getIntent();
      if (intent.hasExtra(CHAVE_NOTA)) {
         setTitle(TITULO_APPBAR_ALTERA);
         Nota nota = (Nota) intent.getSerializableExtra(CHAVE_NOTA);
         posicao = intent.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
         preencheCampos(nota);
      } else {
         setTitle(TITULO_APPBAR_NOVO);
      }
   }

   private void inicializaCampos() {
      campoTitulo = findViewById(R.id.formulario_nota_titulo);
      campoDescricao = findViewById(R.id.formulario_nota_descricao);
   }

   private void preencheCampos(Nota nota) {
      campoTitulo.setText(nota.getTitulo());
      campoDescricao.setText(nota.getDescricao());
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_formulario_nota_salva, menu);
      return super.onCreateOptionsMenu(menu);
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      int itemId = item.getItemId();
      if (ehMenuSalvaNota(itemId)) {
         Nota notaCriada = criaNota();
         enviaNota(notaCriada);
         finish();
      }
      return super.onOptionsItemSelected(item);
   }

   private void enviaNota(Nota nota) {
      Intent intent = new Intent();
      intent.putExtra(CHAVE_NOTA, nota);
      intent.putExtra(CHAVE_POSICAO, posicao);
      setResult(Activity.RESULT_OK, intent);
   }

   @NonNull
   private Nota criaNota() {
      String titulo = campoTitulo.getText().toString();
      String descricao = campoDescricao.getText().toString();
      return new Nota(titulo, descricao);
   }

   private boolean ehMenuSalvaNota(int itemId) {
      return itemId == R.id.menu_formulario_nota_salva;
   }
}