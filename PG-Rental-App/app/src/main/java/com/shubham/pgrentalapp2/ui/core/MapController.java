package com.shubham.pgrentalapp2.ui.map.core;

import com.shubham.pgrentalapp2.model.PgModel;
import java.util.List;

public interface MapController {

    void showUserLocation(double lat, double lng);

    void showPgMarkers(List<PgModel> pgList);

    void moveCamera(double lat, double lng, float zoom);
}
