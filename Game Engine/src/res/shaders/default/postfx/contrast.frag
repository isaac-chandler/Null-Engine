#version 150
in vec2 texCoord;

out vec4 outColor;

uniform sampler2D colors;

uniform float contrast;

void main() {
	outColor = texture(colors, texCoord);
	outColor.rgb = (outColor.rgb - 0.5) * (1 + contrast) + 0.5;
}
