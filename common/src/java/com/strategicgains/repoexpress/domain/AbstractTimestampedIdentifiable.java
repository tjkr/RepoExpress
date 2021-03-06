/*
    Copyright 2011, Strategic Gains, Inc.

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
package com.strategicgains.repoexpress.domain;

import java.util.Date;

import com.strategicgains.repoexpress.domain.TimestampedIdentifiable;

/**
 * @author toddf
 * @since Oct 27, 2011
 */
public abstract class AbstractTimestampedIdentifiable
implements TimestampedIdentifiable
{
	private Date createdAt;
	private Date updatedAt;

	@Override
	public Date getCreatedAt()
	{
		return (createdAt == null ? null : new Date(createdAt.getTime()));
	}

	@Override
	public Date getUpdatedAt()
	{
		return (updatedAt == null ? null : new Date(updatedAt.getTime()));
	}

	@Override
	public void setCreatedAt(Date date)
	{
		this.createdAt = (date == null ? new Date() : new Date(date.getTime()));
	}

	@Override
	public void setUpdatedAt(Date date)
	{
		this.updatedAt = (date == null ? new Date() : new Date(date.getTime()));
	}
}
