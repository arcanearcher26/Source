import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/** Injects the Admin entry/action hook into ba.class without recompiling the
 * obfuscated game classes. */
public final class InjectAdmin2568 {
    private static final String BA = "ba.class";
    private InjectAdmin2568() {}

    public static byte[] patchBa(byte[] original) {
        ClassReader reader = new ClassReader(original);
        ClassWriter writer = new ClassWriter(reader, 0);
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM9, writer) {
            @Override public MethodVisitor visitMethod(int access, String name,
                    String descriptor, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor,
                        signature, exceptions);
                if (name.equals("b") && descriptor.equals("(ILjava/lang/Object;)V")) {
                    return new MethodVisitor(Opcodes.ASM9, mv) {
                        @Override public void visitCode() {
                            super.visitCode();
                            Label continueLabel = new Label();
                            visitVarInsn(Opcodes.ILOAD, 1);
                            visitLdcInsn(110022);
                            visitJumpInsn(Opcodes.IF_ICMPNE, continueLabel);
                            visitMethodInsn(Opcodes.INVOKESTATIC, "AdminBridge", "show", "()V", false);
                            visitInsn(Opcodes.RETURN);
                            visitLabel(continueLabel);
                        }
                    };
                }
                return mv;
            }
        };
        reader.accept(visitor, 0);
        return writer.toByteArray();
    }

    public static byte[] patchMenu(byte[] original) {
        ClassReader reader = new ClassReader(original);
        ClassWriter writer = new ClassWriter(reader, 0);
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM9, writer) {
            @Override public MethodVisitor visitMethod(int access, String name,
                    String descriptor, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor,
                        signature, exceptions);
                if (!name.equals("aa") || !descriptor.equals("()V")) return mv;
                return new MethodVisitor(Opcodes.ASM9, mv) {
                    @Override public void visitMethodInsn(int opcode, String owner,
                            String methodName, String methodDescriptor, boolean itf) {
                        if (opcode == Opcodes.INVOKEVIRTUAL && owner.equals("ce")
                                && methodName.equals("b") && methodDescriptor.equals("(Lcr;)V")) {
                            visitVarInsn(Opcodes.ALOAD, 6);
                            visitTypeInsn(Opcodes.NEW, "y");
                            visitInsn(Opcodes.DUP);
                            visitLdcInsn("Admin");
                            visitLdcInsn(110022);
                            visitMethodInsn(Opcodes.INVOKESPECIAL, "y", "<init>", "(Ljava/lang/String;I)V", false);
                            visitMethodInsn(Opcodes.INVOKEVIRTUAL, "cr", "addElement", "(Ljava/lang/Object;)V", false);
                        }
                        super.visitMethodInsn(opcode, owner, methodName, methodDescriptor, itf);
                    }
                };
            }
        };
        reader.accept(visitor, 0);
        return writer.toByteArray();
    }

    /** Builds the bridge directly as classfile bytecode so no MIDP SDK is
     * required on the host used to assemble the JAR. */
    public static byte[] bridgeClass() {
        ClassWriter w = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        w.visit(Opcodes.V1_3, Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL,
                "AdminBridge", null, "java/lang/Object", new String[] {"bd"});
        w.visitField(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL,
                "CLEAR", "I", null, 1100222).visitEnd();
        MethodVisitor mv = w.visitMethod(Opcodes.ACC_PRIVATE, "<init>", "()V", null, null);
        mv.visitCode(); mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(Opcodes.RETURN); mv.visitMaxs(0, 0); mv.visitEnd();

        mv = w.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "show", "()V", null, null);
        mv.visitCode();
        mv.visitTypeInsn(Opcodes.NEW, "AdminBridge"); mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "AdminBridge", "<init>", "()V", false);
        mv.visitVarInsn(Opcodes.ASTORE, 0);
        mv.visitLdcInsn("Admin");
        mv.visitTypeInsn(Opcodes.NEW, "y"); mv.visitInsn(Opcodes.DUP);
        mv.visitLdcInsn("Thêm Item"); mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitLdcInsn(1100221); mv.visitInsn(Opcodes.ACONST_NULL);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "y", "<init>", "(Ljava/lang/String;Lbd;ILjava/lang/Object;)V", false);
        mv.visitTypeInsn(Opcodes.NEW, "y"); mv.visitInsn(Opcodes.DUP);
        mv.visitLdcInsn("Xóa Hành Trang"); mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitLdcInsn(1100222); mv.visitInsn(Opcodes.ACONST_NULL);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "y", "<init>", "(Ljava/lang/String;Lbd;ILjava/lang/Object;)V", false);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "ax", "a", "(Ljava/lang/String;Ly;Ly;)V", false);
        mv.visitInsn(Opcodes.RETURN); mv.visitMaxs(0, 0); mv.visitEnd();

        mv = w.visitMethod(Opcodes.ACC_PUBLIC, "a", "(ILjava/lang/Object;)V", null, null);
        mv.visitCode();
        Label done = new Label();
        mv.visitVarInsn(Opcodes.ILOAD, 1); mv.visitLdcInsn(1100222);
        mv.visitJumpInsn(Opcodes.IF_ICMPNE, done);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "AdminBridge", "clearInventory", "()V", false);
        mv.visitLabel(done); mv.visitInsn(Opcodes.RETURN); mv.visitMaxs(0, 0); mv.visitEnd();

        mv = w.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "clearInventory", "()V", null, null);
        mv.visitCode();
        Label noPlayer = new Label(), loop = new Label(), after = new Label(), clearDone = new Label();
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "KhanhNguyen9872", "J", "()LKhanhNguyen9872$k0;", false);
        mv.visitVarInsn(Opcodes.ASTORE, 0); mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitJumpInsn(Opcodes.IFNULL, noPlayer); mv.visitInsn(Opcodes.ICONST_0); mv.visitVarInsn(Opcodes.ISTORE, 1);
        mv.visitLabel(loop); mv.visitVarInsn(Opcodes.ILOAD, 1); mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitFieldInsn(Opcodes.GETFIELD, "KhanhNguyen9872$k0", "T", "[S"); mv.visitInsn(Opcodes.ARRAYLENGTH);
        mv.visitJumpInsn(Opcodes.IF_ICMPGE, after); mv.visitVarInsn(Opcodes.ALOAD, 0); mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "KhanhNguyen9872", "c", "(LKhanhNguyen9872$k0;I)V", false);
        mv.visitIincInsn(1, 1); mv.visitJumpInsn(Opcodes.GOTO, loop);
        mv.visitLabel(after); mv.visitMethodInsn(Opcodes.INVOKESTATIC, "KhanhNguyen9872", "P", "()V", false);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "KhanhNguyen9872", "R", "()V", false);
        mv.visitLdcInsn("Đã xóa toàn bộ hành trang."); mv.visitMethodInsn(Opcodes.INVOKESTATIC, "ax", "a", "(Ljava/lang/String;)V", false);
        mv.visitJumpInsn(Opcodes.GOTO, clearDone);
        mv.visitLabel(noPlayer); mv.visitLdcInsn("Không thể truy cập hành trang.");
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "ax", "a", "(Ljava/lang/String;)V", false);
        mv.visitLabel(clearDone); mv.visitInsn(Opcodes.RETURN); mv.visitMaxs(0, 0); mv.visitEnd();
        w.visitEnd(); return w.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 3) throw new IllegalArgumentException("input.jar output.jar bridge.class");
        Path input = Path.of(args[0]);
        Path output = Path.of(args[1]);
        Path bridge = Path.of(args[2]);
        try (JarFile jar = new JarFile(input.toFile())) {
            Manifest manifest = jar.getManifest();
            try (JarOutputStream out = manifest == null
                    ? new JarOutputStream(Files.newOutputStream(output))
                    : new JarOutputStream(Files.newOutputStream(output), manifest)) {
                var entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry old = entries.nextElement();
                    if (old.getName().equals("META-INF/MANIFEST.MF")) continue;
                    JarEntry next = new JarEntry(old.getName());
                    next.setTime(old.getTime());
                    out.putNextEntry(next);
                    byte[] data = jar.getInputStream(old).readAllBytes();
                    if (old.getName().equals(BA)) data = patchMenu(patchBa(data));
                    out.write(data);
                    out.closeEntry();
                }
                JarEntry bridgeEntry = new JarEntry("AdminBridge.class");
                out.putNextEntry(bridgeEntry);
                out.write(bridgeClass());
                out.closeEntry();
            }
        }
    }
}
