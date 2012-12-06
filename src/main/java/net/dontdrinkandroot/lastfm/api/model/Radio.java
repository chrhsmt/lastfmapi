/**
 * Copyright (C) 2012 Philip W. Sorst <philip@sorst.net>
 * and individual contributors as indicated
 * by the @authors tag.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.dontdrinkandroot.lastfm.api.model;
import java.net.URI;

import net.dontdrinkandroot.lastfm.api.model.xspf.XspfPlaylist;
import net.dontdrinkandroot.lastfm.api.queries.AbstractAuthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.queries.AbstractPostQuery;
import net.dontdrinkandroot.lastfm.api.queries.AuthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.queries.PostQuery;
import net.dontdrinkandroot.utils.ISO_639_1;

import org.w3c.dom.Element;

public final class Radio {

	/* Disables constructor */
	private Radio() {
	}

	/**
	 * Fetch new radio content periodically in an XSPF format.
	 *
	 * @param sk
	 *            A session key generated by authenticating a user via the
	 *            authentication protocol. (Required).
	 * @param discovery
	 *            Whether to request last.fm content with discovery mode
	 *            switched on. (Optional).
	 * @param rtp
	 *            Whether the user is scrobbling or not during this radio
	 *            session (helps content generation) (Optional).
	 * @param bitrate
	 *            What bitrate to stream content at, in kbps (supported bitrates
	 *            are 64 and 128) (Optional).
	 * @param buylinks
	 *            Whether the response should contain links for
	 *            purchase/download, if available (default false) (Optional).
	 * @param speedMultiplier
	 *            The rate at which to provide the stream (supported multipliers
	 *            are 1.0 and 2.0) (Optional).
	 * @return
	 */
	public static AuthenticatedGetQuery<XspfPlaylist> getPlaylist(
		final String sk,
		final Boolean discovery,
		final Boolean rtp,
		final Integer bitrate,
		final Boolean buylinks,
		final Float speedMultiplier
	) {

		final AuthenticatedGetQuery<XspfPlaylist> query
			 = new AbstractAuthenticatedGetQuery<XspfPlaylist>("radio.getPlaylist") {
				@Override
				public XspfPlaylist parse(final Element root) {
					return new XspfPlaylist(root);
				}
		};
		query.addParameter("sk", sk);
		query.addParameter("discovery", discovery);
		query.addParameter("rtp", rtp);
		query.addParameter("bitrate", bitrate);
		query.addParameter("buylinks", buylinks);
		query.addParameter("speed_multiplier", speedMultiplier);

		return query;
	}


	/**
	 * Tune in to a Last.fm radio station.
	 *
	 * @param station
	 *            A lastfm radio URL (Required).
	 * @param lang
	 *            An ISO language code to determine the language to return the
	 *            station name in, expressed as an ISO 639 alpha-2 code.
	 *            (Optional).
	 * @param sk
	 *            A session key generated by authenticating a user via the
	 *            authentication protocol. (Required).
	 * @return
	 */
	public static PostQuery<Station> tune(
		final URI station,
		final ISO_639_1 lang,
		final String sk
	) {

		final AbstractPostQuery<Station> query
			 = new AbstractPostQuery<Station>("radio.tune") {
				@Override
				public Station parse(final Element root) {
					final Station station = new Station(root);
					return station;
				}
		};
		if (lang != null) {
			query.addParameter("lang", lang.toString().toLowerCase());
		}
		query.addParameter("station", station);
		query.addParameter("sk", sk);

		return query;
	}


}
