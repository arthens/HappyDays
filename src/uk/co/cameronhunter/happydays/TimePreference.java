package uk.co.cameronhunter.happydays;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.widget.TimePicker;

public class TimePreference extends DialogPreference {

	private Pair<Integer, Integer> value;
	private TimePicker timePicker;

	public TimePreference( Context context, AttributeSet attrs ) {
		super( context, attrs );
		setPersistent( true );
	}

	public TimePreference( Context context, AttributeSet attrs, int defStyle ) {
		super( context, attrs, defStyle );
		setPersistent( true );
	}

	@Override
	protected View onCreateDialogView() {
		value = getPersistedValue();

		timePicker = new TimePicker( getContext() );
		timePicker.setIs24HourView( true );
		timePicker.setCurrentHour( value.first );
		timePicker.setCurrentMinute( value.second );

		return timePicker;
	}

	@Override
	protected void onDialogClosed( boolean positiveResult ) {
		if ( positiveResult ) {
			if ( isPersistent() ) {
				persistString( valueToString( Pair.create( timePicker.getCurrentHour(), timePicker.getCurrentMinute() ) ) );
			}
			callChangeListener( positiveResult );
		}
	}

	public String getValue() {
		return valueToString( getPersistedValue() );
	}

	private Pair<Integer, Integer> getPersistedValue() {
		return stringToValue( getPersistedString( getContext().getString( R.string.default_time ) ) );
	}
	
	private Pair<Integer, Integer> stringToValue( String value ) {
		if ( value == null ) return null;
		String[] parts = value.split( getContext().getString( R.string.time_join_character ) );
		return Pair.create( Integer.parseInt( parts[0] ), Integer.parseInt( parts[1] ) );
	}

	private String valueToString( Pair<Integer, Integer> value ) {
		Context context = getContext();
		return context.getString( R.string.time_format, value.first, context.getString( R.string.time_join_character ), value.second );
	}

}
