//
// The contents of this file are subject to the terms
// of the Common Development and Distribution License
// (the License).  You may not use this file except in
// compliance with the License.
// 
// You can obtain a copy of the license at
// https://woodstock.dev.java.net/public/CDDLv1.0.html.
// See the License for the specific language governing
// permissions and limitations under the License.
// 
// When distributing Covered Code, include this CDDL
// Header Notice in each file and include the License file
// at https://woodstock.dev.java.net/public/CDDLv1.0.html.
// If applicable, add the following below the CDDL Header,
// with the fields enclosed by brackets [] replaced by
// you own identifying information:
// "Portions Copyrighted [year] [name of copyright owner]"
// 
// Copyright 2007 Sun Microsystems, Inc. All rights reserved.
//

dojo.provide("webui.@THEME@.theme.common");

dojo.require("dojo.i18n.common");

/**
 * Define the theme namespace. This namespace contains
 * methods to obtain theme values. It is also the base of the
 * dojo nls localized theme namespace.
 * 
 * The client theme is composed of the following theme categories.
 * <ul>
 * <li>messages</li>
 * <li>styles</li>
 * <li>images</li>
 * <li>templates</li>
 * </ul>
 * Each category has a set of properties. See the methods in
 * webui.@THEME@.theme.common for obtaining the theme property values.
 *
 * dojo.requireLocalization is reimplemented here in order to perform
 * hierarchical extension of the theme for application theme overrides.
 *
 * NOTE THE SPACE AFTER THE "dojo." SEGMENT, IN  REFERENCES TO DOJO 
 * METHODS THAT LOAD A MODULE. IF THERE IS NO SPACE "debugAtAllCosts"
 * RESULTS IN JAVASCRIPT ERRORS DUE TO PATTERN MATCHING BY DOJO TO 
 * FIND MODULE LOADING METHODS.
 */
webui.@THEME@.theme.common = {


    /**
     * This function is used to set widget properties with the
     * following Object literals.
     *
     * <ul>
     *  <li>bundle - the javascript theme basename "sunttheme" for
     * @THEME@.js</li> 
     *  <li>locale - locale in dojo form <lang>-<country>-<variant></li>
     *  <li>module - the javascript namespace into which dojo will load the
     * theme properties.</li>
     *  <li>modulePath - a relative URL defining the root directory of the nls
     * directory.</li>
     *  <li>prefix - the application context and the theme servlet context.</li>
     *  <li>custom - an array of basenames identifying an application's 
     * javascript theme files. The last segment of this "dot" separated 
     * string, is treated as the "bundle", and the initial segments are
     * treated as the module path.</li>
     * </ul>
     *
     * @param props Key-Value pairs of properties.
     */
    initialize: function(props) {
        if (props == null) {
            return false;
        }

	webui.@THEME@.theme.common.modulePath = null;
        webui.@THEME@.theme.common.prefix = props.prefix;
        webui.@THEME@.theme.common.module = props.module;
        webui.@THEME@.theme.common.bundle = props.bundle;
        webui.@THEME@.theme.common.locale = props.locale;

	if (props.modulePath != null && props.modulePath != "") {
	    webui.@THEME@.theme.common.modulePath = props.prefix;
	    if (props.modulePath.charAt(0) != '/') {
		webui.@THEME@.theme.common.modulePath = 
			webui.@THEME@.theme.common.modulePath + "/";
	    }
	    webui.@THEME@.theme.common.modulePath = 
		webui.@THEME@.theme.common.modulePath + props.modulePath;
	    dojo. hostenv.setModulePrefix(props.module, 
		webui.@THEME@.theme.common.modulePath);
	}

        // Load the javascript theme
        //
        webui.@THEME@.theme.common.requireLocalization(props.module, 
            props.bundle, props.locale);

        webui.@THEME@.theme.common.baseTheme = dojo. i18n.getLocalization(
            props.module, props.bundle, props.locale);

        if (props.custom instanceof Array) {
            for (var i = 0; i < props.custom.length; ++i) {
                webui.@THEME@.theme.common.extendBaseTheme(props.custom[i]);
            }
        } else if (typeof(props.custom) == "string") {
            webui.@THEME@.theme.common.extendBaseTheme(props.custom);
        }
        return true;
    },

    /**
     * Return the theme prefix, this is the application context
     * concatenated with the theme servlet context.
     */
    getPrefix : function() {
	return webui.@THEME@.theme.common.prefix;
    },

    /**
     * Returns a theme property "theme[category][key]" or null, never "".
     */
    getProperty: function(category, key) {
        try {
            var p = webui.@THEME@.theme.common.baseTheme[category][key];
            return p == null || p == "" ? null : p;
        } catch (e) {
            return null;
        }
    },

    /**
     * Returns the theme properties for a theme category or null.
     */
    getProperties: function(category) {
        try {
            var p = webui.@THEME@.theme.common.baseTheme[category];
            return p == null || p == "" ? null : p;
        } catch (e) {
            return null;
        }
    },

    /**
     * Returns a formatted message if "params" is not null, else
     * the literal value of "getProperty("messages", prop) or
     * null if there is no value for property. "params" can be
     * a comma separated list of arguments, or an object.
     */
    getMessage : function(property, params) {
	var msg = webui.@THEME@.theme.common.getProperty("messages", property);
	if (msg == null) {
	    return null;
	}
	if (params != null) {
	    return dojo.string.substituteParams(msg, params);
	} else {
	    return msg;
	}
    },

    /**
     * Returns the followin Object literals, else null.
     * <ul>
     * <li>url - the image path with the theme prefix</li>
     * <li>width - image width</li>
     * <li>height - image height</li>
     * <li>title - value for the "title" attribute</li>
     * <li>alt - value for the "alt" attribute</li>
     * </ul>
     * If "alt" or "title" are message properties and not a literal value
     * the property is resolved to its message value.
     * This method should be called with the actual message property
     * and not one of its variants like "ALARM_CRITICAL_ALT". Use
     * "webui.@THEME@.theme.common.getProperty("images", "ALARM_CRITICAL_ALT")"
     * to get individual values if desired.
     * If the literal path is desired, without the prefix, use
     * "webui.@THEME@.theme.common.getProperty("images", imageprop)"
     * where imageprop is the actual image property like "ALARM_CRITICAL".
     */
    getImage : function(property) {

	var props = ["url", "alt", "title", "width", "height"];
	var path = webui.@THEME@.theme.common.getProperty("images", property);
	if (path == null) {
	    return null;
	}
	var image = {};
	var j = 0;
	image[props[j++]] = webui.@THEME@.theme.common.getPrefix() + path;

	var imageprops = [property + "_ALT", property + "_TITLE"];
	for (var i = 0; i < imageprops.length; ++i) {
	    var value = webui.@THEME@.theme.common.getProperty("images", 
		imageprops[i]);
	    if (value != null) {
		var msg = webui.@THEME@.theme.common.getMessage(value, null);
		if (msg != null) {
		    value = msg;
		}
	    }
	    image[props[j++]] = value;
	}
	imageprops = [property + "_WIDTH", property + "_HEIGHT"];
	for (var i = 0; i < imageprops.length; ++i) {
	    image[props[j++]] =
		webui.@THEME@.theme.common.getProperty("images", imageprops[i]);
	}
	return image;
    },

    /**
     * Return the selector from the "styles" theme category for property
     * else null.
     */
    getStyleClass : function(property) {
	return webui.@THEME@.theme.common.getProperty("styles", property);
    },

    /**
     * This function is used to obtain a template path, or returns null
     * if key is not found or is not a path, i.e. begins with "<".
     *
     * @param key A key defining a theme "templates" property.
     */
    getTemplatePath: function(key) {

        var template = webui.@THEME@.theme.common.getProperty("templates", key);
        if (template != null && template.charAt(0) != '<') {
            return themePrefix + "/" + template;
        } else {
            return null;
        }
    },

    /**
     * This function is used to obtain a template string, or returns null
     * if key is not found or is not a string, i.e. does not begin with "<".
     *
     * @param key A key defining a theme "templates" property.
     */
    getTemplateString: function(key) {

        var template = webui.@THEME@.theme.common.getProperty("templates", key);

        if (template != null && template.charAt(0) == '<') {
            return template;
        } else {
            return null;
        }
    },

    /**
     * Merge the baseTheme with an application's theme overrides
     * "themePackage" is a "dot" separated string. The "bundle"
     * is the last segment and the prefix segments are the module.
     * Return true if the base theme was extended else false.
     */
    extendBaseTheme: function(themePackage) {
        if (themePackage == null || themePackage == "") {
            return false;
        }
        var segments = themePackage.split(".");
        var bundle = segments[segments.length - 1];
        var module = segments.slice(0, segments.length - 1).join(".");

        try {
            // If there is no module, i.e. just a bundle segment
            // create a module name in the theme namespace.
            //
            var prefix = webui.@THEME@.theme.common.prefix;
            if (module == null || module == "") {
                    webui.@THEME@.theme.common.custom = {};
                    module = "webui.@THEME@.theme.common.custom";
            } else {
                var re = new RegExp("\\.", "g");
                prefix = prefix + "/" + module.replace(re, "/");
            }
            // NOTE: Shouldn't need to set a module prefix to obtain a module???
	    // Only when the theme files are located under the initial
	    // set prefix for "webui.@THEME@."
	    // If they are not located there then a prefix must be set.
	    // For example an application's theme javascript files.
	    //
            dojo. hostenv.setModulePrefix(module, prefix);

            webui.@THEME@.theme.common.requireLocalization(
                module, bundle, webui.@THEME@.theme.common.themeLocale);
        } catch(e) {
	    // dojo.debug(e)
	    return false;
        }
        var newTheme = null;
        try {
            newTheme = dojo. i18n.getLocalization(module, bundle, 
                webui.@THEME@.theme.common.themeLocale);
        } catch(e) {
	    // dojo.debug(e)
	    return false;
        }
        // Not sure if we should do the "prototype" magic like
        // dojo, vs. just replacing the orginal baseTheme values.
        //
        if (newTheme != null) {
            webui.@THEME@.theme.common.extend(
                webui.@THEME@.theme.common.baseTheme, newTheme);
        }
        return true;
    },

    /**
     * Extend "theme" with "props". "props" is organized hierarchically
     */
    extend: function(theme, props) {
        if (theme == null || props == null) {
            return false;
        }
        for (var property in props) {
            if (theme[property] && typeof theme[property] == "object") {
                webui.@THEME@.theme.common.extend(theme[property], props[property]);
            } else {
                theme[property] = props[property];
            }
        }
        return true;
    },

    /**
     * Taken from dojo.js in order to override the 
     * callback function that is passed to loadPath, in to perform
     * hierarchical "extension" of properties.
     */
    requireLocalization: function(/*String*/moduleName, /*String*/bundleName, 
        /*String?*/locale, /*String?*/availableFlatLocales){

// summary:
//      Declares translated resources and loads them if necessary, 
//       in the same style as dojo.require.
//      Contents of the resource bundle are typically strings, but may be 
//      any name/value pair,
//      represented in JSON format.  See also dojo.i18n.getLocalization.
//
// moduleName: name of the package containing the "nls" directory in 
//      which the bundle is found
// bundleName: bundle name, i.e. the filename without the '.js' suffix
// locale: the locale to load (optional)  By default, the browser's 
//      user locale as defined by dojo.locale
// availableFlatLocales: A comma-separated list of the available, 
//      flattened locales for this bundle.
// This argument should only be set by the build process.
//
// description:
//      Load translated resource bundles provided underneath the "nls" 
//      directory within a package.
//      Translated resources may be located in different packages throughout 
//      the source tree.  For example,
//      a particular widget may define one or more resource bundles, 
//      structured in a program as follows,
//      where moduleName is mycode.mywidget and bundleNames available 
//      include bundleone and bundletwo:
//      ...
//      mycode/
//       mywidget/
//        nls/
//         bundleone.js (the fallback translation, English in this example)
//         bundletwo.js (also a fallback translation)
//         de/
//          bundleone.js
//          bundletwo.js
//         de-at/
//          bundleone.js
//         en/
//          (empty; use the fallback translation)
//         en-us/
//          bundleone.js
//         en-gb/
//          bundleone.js
//         es/
//          bundleone.js
//          bundletwo.js
//        ...etc
//      ...
//      Each directory is named for a locale as specified by 
//      RFC 3066, (http://www.ietf.org/rfc/rfc3066.txt),
//      normalized in lowercase.  Note that the two bundles in the 
//      example do not define all the same variants.
//      For a given locale, bundles will be loaded for that locale and 
//      all more general locales above it, including
//      a fallback at the root directory.  For example, a declaration 
//      for the "de-at" locale will first
//      load nls/de-at/bundleone.js, then nls/de/bundleone.js and 
//      finally nls/bundleone.js.  The data will
//      be flattened into a single Object so that lookups will follow this 
//      cascading pattern.  An optional build
//      step can preload the bundles to avoid data redundancy and the 
//      multiple network hits normally required to
//      load these resources.

        dojo. hostenv.preloadLocalizations();
        var targetLocale = dojo. hostenv.normalizeLocale(locale);
        var bundlePackage = [moduleName, "nls", bundleName].join(".");

//NOTE: When loading these resources, the packaging does not match 
// what is on disk.  This is an
// implementation detail, as this is just a private data structure to 
// hold the loaded resources.
// e.g. tests/hello/nls/en-us/salutations.js is loaded as the 
// object tests.hello.nls.salutations.en_us={...}
// The structure on disk is intended to be most convenient for developers 
// and translators, but in memory
// it is more logical and efficient to store in a different order.  
// Locales cannot use dashes, since the
// resulting path will not evaluate as valid JS, so we translate them 
// to underscores.
        
    // Find the best-match locale to load if we have available flat locales.
    var bestLocale = "";
    if(availableFlatLocales){
        var flatLocales = availableFlatLocales.split(",");
        for(var i = 0; i < flatLocales.length; i++){
            //Locale must match from start of string.
            if(targetLocale.indexOf(flatLocales[i]) == 0){
                if(flatLocales[i].length > bestLocale.length){
                    bestLocale = flatLocales[i];
                }
            }
        }
        if(!bestLocale){
            bestLocale = "ROOT";
        }               
    }

    // See if the desired locale is already loaded.
    var tempLocale = availableFlatLocales ? bestLocale : targetLocale;
    var bundle = dojo. hostenv.findModule(bundlePackage);
    var localizedBundle = null;
    if(bundle){
        if(djConfig.localizationComplete && bundle._built){return;}
        var jsLoc = tempLocale.replace('-', '_');
        var translationPackage = bundlePackage+"."+jsLoc;
        localizedBundle = dojo. hostenv.findModule(translationPackage);
    }

    if(!localizedBundle){
        bundle = dojo. hostenv.startPackage(bundlePackage);
        var syms = dojo. hostenv.getModuleSymbols(moduleName);
        var modpath = syms.concat("nls").join("/");
        var parent;

        dojo. hostenv.searchLocalePath(tempLocale, 
                availableFlatLocales, function(loc){
            var jsLoc = loc.replace('-', '_');
            var translationPackage = bundlePackage + "." + jsLoc;
                var loaded = false;
                if(!dojo. hostenv.findModule(translationPackage)){
                    // Mark loaded whether it's found or not, 
                    // so that further load attempts will not 
                    // be made
                    dojo. hostenv.startPackage(translationPackage);
                    var module = [modpath];
                    if(loc != "ROOT"){module.push(loc);}
                    module.push(bundleName);
                    var filespec = module.join("/") + '.js';

                    loaded = dojo. hostenv.loadPath(filespec, null,
                        function(hash){
                            // Use singleton with prototype to point to parent
                            // bundle, then mix-in result from loadPath
                            var clazz = function(){};
                            clazz.prototype = parent;
                            bundle[jsLoc] = new clazz();
                            // Use "hierarchical" extend.
                            // for(var j in hash){ bundle[jsLoc][j] = hash[j]; }
                            webui.@THEME@.theme.common.extend(bundle[jsLoc], hash);
                        });
                }else{
                    loaded = true;
                }
                if(loaded && bundle[jsLoc]){
                    parent = bundle[jsLoc];
                }else{
                    bundle[jsLoc] = parent;
                }
                
                if(availableFlatLocales){
                    //Stop the locale path searching if we 
                    // know the availableFlatLocales, since
                    //the first call to this function will 
                    // load the only bundle that is needed.
                        return true;
                }
            });
        }

        // Save the best locale bundle as the target locale bundle 
        // when we know the
        // the available bundles.
        //
        if(availableFlatLocales && targetLocale != bestLocale){
                bundle[targetLocale.replace('-', '_')] = 
                        bundle[bestLocale.replace('-', '_')];
        }
    }
}

// Initialize theme.
webui.@THEME@.theme.common.initialize(djConfig["webui.@THEME@.theme"]);