public class Test {
	public static void main(String[] args) {
		NewList list = new NewList();
		
		list.add( "Every" );
		list.add( "Good" );
		list.add( "Boy" );
		list.add( "Deserves" );
		
		if( list.get( 1 ).equals( "Good" ) )
			System.out.println("Test 1 passed!");
		else
			System.out.println("Test 1 failed!");
			
		NewList list2 = new NewList();
		
		list2.add( "I" );
		list2.add( "Believe" );
		
		list2.addAll( list );
		
		if( list2.get( 4 ) == "Boy" )
			System.out.println("Test 2 passed!");
		else
			System.out.println("Test 2 failed!");
			
		list.clear();
		
		if( list.isEmpty() )
			System.out.println("Test 3 passed!");
		else
			System.out.println("Test 3 failed!");
		
		if( list2.size() == 6 )
			System.out.println("Test 4 passed!");
		else
			System.out.println("Test 4 failed!" + list2.size());
			
		list2.remove( 3 );
		
		if( list2.indexOf( "Boy" ) == 3 )
			System.out.println("Test 5 passed!");
		else
			System.out.println("Test 5 failed!");
		//list2.readOut();
		list2.set(3, "Dog");
		
		if( list2.indexOf( "Dog" ) == 3 )
			System.out.println("Test 6 passed!");
		else
			System.out.println("Test 6 failed!");

	}
}