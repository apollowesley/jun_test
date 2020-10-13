/*Copyright ©2016 TommyLemon(https://github.com/TommyLemon/APIJSON)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package apijson.boot.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import apijson.framework.APIJSONApplication;
import apijson.framework.APIJSONCreator;
import apijson.orm.SQLConfig;


/**SpringBootApplication
 * 右键这个类 > Run As > Java Application
 * @author Lemon
 */
@Configuration
@SpringBootApplication
public class DemoApplication {

	static {
		APIJSONApplication.DEFAULT_APIJSON_CREATOR = new APIJSONCreator() {
			@Override
			public SQLConfig createSQLConfig() {
				return new DemoSQLConfig();
			}
		};
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(DemoApplication.class, args);
        System.out.println("\n\n<<<<<<<<<<<<<<<<<<<<<<<<< APIJSON 启动完成，试试调用自动化 API 吧 ^_^ >>>>>>>>>>>>>>>>>>>>>>>>\n");
	}

}
