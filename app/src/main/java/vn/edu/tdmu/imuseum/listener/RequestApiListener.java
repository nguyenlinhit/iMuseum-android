package vn.edu.tdmu.imuseum.listener;

import java.util.List;

import vn.edu.tdmu.imuseum.model.Artifact;

/**
 * Created by nvulinh on 11/10/17.
 *
 */

public interface RequestApiListener extends RequsetListener{
    void onRequestDone(List<Artifact> response);
}
