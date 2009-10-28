@TypeDefs({
	@TypeDef(name = "datetime", typeClass = PersistentDateTime.class),
	@TypeDef(name = "datetimetz", typeClass = PersistentDateTimeTZ.class),
	@TypeDef(name = "duration", typeClass = PersistentDuration.class),
	@TypeDef(name = "interval", typeClass = PersistentInterval.class),	
	@TypeDef(name = "localdate", typeClass = PersistentLocalDate.class),	
	@TypeDef(name = "localdatetime", typeClass = PersistentLocalDateTime.class),	
	@TypeDef(name = "localtimeasstring", typeClass = PersistentLocalTimeAsString.class),	
	@TypeDef(name = "localtimeastime", typeClass = PersistentLocalTimeAsTime.class),	
	@TypeDef(name = "localtimeexact", typeClass = PersistentLocalTimeExact.class),	
	@TypeDef(name = "period", typeClass = PersistentPeriod.class),	
	@TypeDef(name = "timeofday", typeClass = PersistentTimeOfDay.class),	
	@TypeDef(name = "timeofdayexact", typeClass = PersistentTimeOfDayExact.class)	
})
package com.google.code.trapo.domain;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.joda.time.contrib.hibernate.PersistentDateTime;
import org.joda.time.contrib.hibernate.PersistentDateTimeTZ;
import org.joda.time.contrib.hibernate.PersistentDuration;
import org.joda.time.contrib.hibernate.PersistentInterval;
import org.joda.time.contrib.hibernate.PersistentLocalDate;
import org.joda.time.contrib.hibernate.PersistentLocalDateTime;
import org.joda.time.contrib.hibernate.PersistentLocalTimeAsString;
import org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime;
import org.joda.time.contrib.hibernate.PersistentLocalTimeExact;
import org.joda.time.contrib.hibernate.PersistentPeriod;
import org.joda.time.contrib.hibernate.PersistentTimeOfDay;
import org.joda.time.contrib.hibernate.PersistentTimeOfDayExact;

