package br.com.dtfoods.ceep.ui.activity;

import static br.com.dtfoods.ceep.ui.activity.NotasActivityConstantes.CHAVE_NOTA;
import static br.com.dtfoods.ceep.ui.activity.NotasActivityConstantes.CODIGO_RESULTADO_NOTA_CRIADA;

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

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_formulario_nota);
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
      setResult(CODIGO_RESULTADO_NOTA_CRIADA, intent);
   }

   @NonNull
   private Nota criaNota() {
      EditText campoTitulo = findViewById(R.id.formulario_nota_titulo);
      String titulo = campoTitulo.getText().toString();
      EditText campoDescricao = findViewById(R.id.formulario_nota_descricao);
      String descricao = campoDescricao.getText().toString();
      return new Nota(titulo, descricao);
   }

   private boolean ehMenuSalvaNota(int itemId) {
      return itemId == R.id.menu_formulario_nota_salva;
   }
}