package images;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import messif.objects.LocalAbstractObject;
import messif.objects.MetaObject;
import messif.objects.impl.ObjectIntVectorL1;
import messif.objects.impl.ObjectShortVectorL1;

/**
 * Specific implementation of the {@link MetaObject} for images.
 *
 * @author xbatko
 */
public class ImagesMetaObject extends MetaObject {
    /** Serial version of this object used for {@link java.io.Serializable} */
    private final static long serialVersionUID = 1L;

    /** Name of the encapsulated color structure descriptor */
    private static final String colorStructureName = "ColorStructureType";
    /** Encapsulated color structure descriptor */
    private final ObjectShortVectorL1 colorStructure;
    /** Name of the encapsulated scalable color descriptor */
    private static final String scalableColorName = "ScalableColorType";
    /** Encapsulated scalable color descriptor */
    private final ObjectIntVectorL1 scalableColor;

    /**
     * Creates a new instance of ImagesMetaObject with the given color structure
     * and scalable color descriptors and a locator identifier.
     * @param locatorURI the identifier of this object
     * @param colorStructure the color structure descriptor to encapsulate
     * @param scalableColor the scalable color descriptor to encapsulate
     */
    public ImagesMetaObject(String locatorURI, ObjectShortVectorL1 colorStructure, ObjectIntVectorL1 scalableColor) {
        super(locatorURI);
        this.colorStructure = colorStructure;
        this.scalableColor = scalableColor;
    }

    /**
     * Creates a new ImagesMetaObject instance by reading the data from a text stream.
     * @param stream the text stream from which to read the data
     * @throws IOException if there was an I/O error reading the data from the stream
     */
    public ImagesMetaObject(BufferedReader stream) throws IOException {
        String header = readObjectComments(stream);

        String[] descriptors = header.split(";");
        ObjectIntVectorL1 sc = null;
        ObjectShortVectorL1 cs = null;
        for (int i = 0; i < descriptors.length; i += 2) {
            if (descriptors[i].equals(scalableColorName))
                sc = new ObjectIntVectorL1(stream);
            else if (descriptors[i].equals(colorStructureName))
                cs = new ObjectShortVectorL1(stream);
        }
        this.scalableColor = sc;
        this.colorStructure = cs;
    }

    @Override
    protected void writeData(OutputStream stream) throws IOException {
        if (scalableColor != null) {
            stream.write(scalableColorName.getBytes());
            stream.write(';');
            stream.write(scalableColor.getClass().getName().getBytes());
        }
        if (colorStructure != null) {
            if (scalableColor != null)
                stream.write(';');
            stream.write(colorStructureName.getBytes());
            stream.write(';');
            stream.write(colorStructure.getClass().getName().getBytes());
        }
        stream.write('\n');
        if (scalableColor != null)
            scalableColor.write(stream);
        if (colorStructure != null)
            colorStructure.write(stream);
    }

    @Override
    public int getObjectCount() {
        return 2;
    }

    @Override
    public LocalAbstractObject getObject(String name) {
        if (name.equals(scalableColorName))
            return scalableColor;
        else if (name.equals(colorStructureName))
            return colorStructure;
        return null;
    }

    @Override
    public Collection<String> getObjectNames() {
        return Arrays.asList(scalableColorName, colorStructureName);
    }

    @Override
    public Collection<LocalAbstractObject> getObjects() {
        return Arrays.asList((LocalAbstractObject)scalableColor, colorStructure);
    }

    @Override
    protected float getDistanceImpl(LocalAbstractObject obj, float[] metaDistances, float distThreshold) {
        ImagesMetaObject object = (ImagesMetaObject)obj;
        float dist = 0;

        if (scalableColor != null && object.scalableColor != null) {
            float d = scalableColor.getDistance(object.scalableColor);
            dist += d;
            if (metaDistances != null)
                metaDistances[0] = d;
        }

        if (colorStructure != null && object.colorStructure != null) {
            float d = colorStructure.getDistance(object.colorStructure);
            dist += d;
            if (metaDistances != null)
                metaDistances[1] = d;
        }

        return dist;
    }

}
