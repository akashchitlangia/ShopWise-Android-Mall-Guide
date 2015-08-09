package com.utopian.shopwise;

import java.util.ArrayList;
import java.util.List;



/*
Copyright 2012 Aphid Mobile

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
 
   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

 */
public class Travels {

  public static final List<Data> IMG_DESCRIPTIONS = new ArrayList<Data>();

  static {
	    Travels.IMG_DESCRIPTIONS.add(new Travels.Data(Mall_Specific.class,Mall_Specific.class,"ti.png","c21.png","Treasure Island","Century 21","MG Road","AB Road","4","4.5"));
	    Travels.IMG_DESCRIPTIONS.add(new Travels.Data(Mall_Specific.class,Mall_Specific.class,"central.png","mangal.png","Central Mall","Mangal City Mall","RNT Marg","Vijay Nagar","4","4.5"));
	    Travels.IMG_DESCRIPTIONS.add(new Travels.Data(Mall_Specific.class,Mall_Specific.class,"icon_malhar.png","central.png","Malhar Mega Mall","Central Mall","AB Road","RNT Marg","4","4.5"));

	    
	    
	    //ravels.IMG_DESCRIPTIONS.add(new Travels.Data(FlipAsyncContentActivity.class));,"malhar.jpg"
	    
	   
}

public static final class Data {

	  // public final name;
	    public final String imageFilename1;
	    public final String imageFilename2;
	    
	    public final String address1;
	    public final String address2;
	  
	    
	    public final  String mall_name1;
	    public final String mall_name2;
	    
	    public final  String rating1;
	    public final String rating2;
	    
	   // public final String country;
	    //public final String city;
	    //public final String link;

	  //  public static Class<?> name;
	  public Class name;
		public Class name1;
		

		private Data(Class name, Class class1 ,String imageFilename1,String imageFilename2,String mall_name1,String mall_name2,String mall_add1,String mall_add2,String r1,String r2) {
	      this.name = name;
	      this.name1=class1;
	     
	      this.imageFilename1=imageFilename1;
	      this.imageFilename2=imageFilename2;
	
	      this.mall_name1=mall_name1;
	      this.mall_name2=mall_name2;
	      this.address1=mall_add1;
	      this.address2=mall_add2;
	      this.rating1=r1;
	      this.rating2=r2;
	    
	      
	    }
		
	  }
}


