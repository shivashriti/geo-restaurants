import com.google.inject.AbstractModule

import utils.AppDAO
import utils.AppDAOImpl


class Module extends AbstractModule {

  override def configure() = {

    // Set AppDAOImpl as the implementation for AppDAO
    bind(classOf[AppDAO]).to(classOf[utils.AppDAOImpl])
  }

}
