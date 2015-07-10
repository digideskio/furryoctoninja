package pl.rspective.survey.ui.common;

import java.util.List;

public interface AdapterListener<T> {

    void updateData(List<T> data);

    void onItemClick(int position);

}
