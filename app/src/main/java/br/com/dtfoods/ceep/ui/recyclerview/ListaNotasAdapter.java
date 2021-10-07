package br.com.dtfoods.ceep.ui.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.dtfoods.ceep.R;
import br.com.dtfoods.ceep.model.Nota;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {

   private static final String TAG = "ListaNotaAdapter";

   private final List<Nota> notas;
   private final Context context;

   public ListaNotasAdapter(Context context, List<Nota> notas) {
      this.context = context;
      this.notas = notas;
   }

   @NonNull
   @Override
   public ListaNotasAdapter.NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View viewCriada = LayoutInflater.from(context)
              .inflate(R.layout.item_nota, parent, false);
      return new NotaViewHolder(viewCriada);
   }

   @Override
   public void onBindViewHolder(@NonNull ListaNotasAdapter.NotaViewHolder holder, int position) {
      Nota nota = notas.get(position);
      holder.vinculaView(nota);
   }

   @Override
   public int getItemCount() {
      return notas.size();
   }

   public void adiciona(Nota nota) {
      notas.add(nota);
      notifyItemInserted(notas.indexOf(nota));
   }

   static class NotaViewHolder extends RecyclerView.ViewHolder {

      private final TextView campoTitulo;
      private final TextView campoDescricao;

      public NotaViewHolder(@NonNull View itemView) {
         super(itemView);
         
         campoTitulo = itemView.findViewById(R.id.item_nota_titulo);
         campoDescricao = itemView.findViewById(R.id.item_nota_descricao);
      }

      public void vinculaView(Nota nota) {
         campoTitulo.setText(nota.getTitulo());
         campoDescricao.setText(nota.getDescricao());
      }
   }
}
