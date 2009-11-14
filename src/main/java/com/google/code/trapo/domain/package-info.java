@TypeDefs({
	@TypeDef(name = "datetime", typeClass = PersistentDateTime.class)
})
@NamedQueries({
	@NamedQuery(
		name = "Forum.byName", 
		query = "from Forum f where f.name = ?"
	),
	@NamedQuery(
		name = "Topic.topicsFor",
		query = "from Topic t where t.forum.id = ? order by createdAt desc"
	),
	@NamedQuery(
		name = "Topic.byTitle", 
		query="from Topic t where t.title = ?"
	)
})
package com.google.code.trapo.domain;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.joda.time.contrib.hibernate.PersistentDateTime;

