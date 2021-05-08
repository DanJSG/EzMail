package com.jsg.ezmail;

import javax.activation.DataSource;

/**
 * An email containing HTML body content, a subject, and optional images. If images are not present, these will be
 * returned as {@code null}.
 *
 * This email can be sent using the {@code EmailSender} class method {@code send(email, to)}.
 *
 */
public interface Email {

    /**
     * Get the subject line of the email.
     *
     * @return the email subject line
     */
    String getSubject();

    /**
     * Get the email's HTML content.
     *
     * @return the email's HTML content as a {@code String}
     */
    String getHtmlContent();

    /**
     * Get the email's images as an array of data sources.
     *
     * @return an array of {@code DataSource} objects representing images
     */
    DataSource[] getImages();

}
