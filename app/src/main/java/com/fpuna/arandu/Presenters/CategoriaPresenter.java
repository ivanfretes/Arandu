package com.fpuna.arandu.Presenters;

import android.os.AsyncTask;

import com.fpuna.arandu.Clases.Constante;
import com.fpuna.arandu.Interfaces.ICategoria;
import com.fpuna.arandu.Models.CategoriaModel;

public class CategoriaPresenter implements ICategoria.Presenter {
    private ICategoria.Model model;
    private ICategoria.View view;

    public CategoriaPresenter(ICategoria.View view) {
        this.view = view;
        this.model = new CategoriaModel();
    }

    @Override
    public void consultar() {

        view.showProgressDialog();
        AsyncCategoria asyncCategoria = new AsyncCategoria();
        asyncCategoria.execute();

    }


    private void loadView() {

        view.cargarAdapter(model.getCategorias());

    }

    private class AsyncCategoria extends AsyncTask<Void, Void, Void> {

        private Throwable throwable;
        @Override
        protected Void doInBackground(Void... voids) {
            model.setContext(view.getContext());
            try {
                model.consultar();
            } catch (Exception e) {
                e.printStackTrace();
                throwable = e;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            view.dismissProgressDialog();
            if(throwable != null) {
                view.showMessage(throwable.getMessage());
            }else{
                loadView();
            }

        }
    }
}
